package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;
import hr.fer.zemris.math.NewtonRaphson;
import hr.fer.zemris.util.ComplexNumberParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Used for visualizing Newton Raphson iteration fractal
 * <p>
 * This version doesn't utilize multi-threading
 */
public class Newton {

    public static void main(String[] args) {
        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

        List<Complex> roots = new ArrayList<>();

        try (Scanner sc = new Scanner(System.in)) {
            int rootIndex = 1;
            System.out.printf("Root %d> ", rootIndex++);
            String line = sc.nextLine();

            while(!line.equalsIgnoreCase("done")) {
                roots.add(ComplexNumberParser.parse(line));
                System.out.printf("Root %d> ", rootIndex++);
                line = sc.nextLine();
            }

            if (rootIndex <= 3) {
                System.out.println("You must provide at least two roots.");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[2]));

        System.out.println("Image of fractal will appear shortly. Thank you.");
        FractalViewer.show(new NewtonRaphsonProducer(crp));
    }

    private static class NewtonRaphsonProducer implements IFractalProducer {

        private final ComplexRootedPolynomial rootedPolynomial;

        public NewtonRaphsonProducer(ComplexRootedPolynomial rootedPolynomial) {
            this.rootedPolynomial = rootedPolynomial;
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            System.out.println("Zapocinjem izracun...");

            short[] data = new short[width * height];

            NewtonRaphson.calculate(rootedPolynomial, reMin, reMax, imMin, imMax, width, height, 0, height, data, cancel);

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short)(rootedPolynomial.toComplexPolynomial().order() + 1), requestNo);
        }
    }
}
