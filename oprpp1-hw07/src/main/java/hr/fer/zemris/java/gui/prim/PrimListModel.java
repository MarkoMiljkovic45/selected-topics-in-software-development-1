package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class PrimListModel implements ListModel<Integer> {

    private final List<Integer> elements;
    private final List<ListDataListener> listeners;

    public PrimListModel() {
        this.elements = new ArrayList<>();
        this.listeners = new ArrayList<>();

        elements.add(1);
        notifyListeners();
    }

    @Override
    public int getSize() {
        return elements.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return elements.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    public void next() {
        int prim = elements.get(elements.size() - 1) + 1;

        while(!isPrim(prim))
            prim++;

        elements.add(prim);
        notifyListeners();
    }

    private boolean isPrim(int n) {
        int bound = n / 2 + 1;

        for (int i = 2; i < bound; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    private void notifyListeners() {
        listeners.forEach(l -> l.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, elements.size() - 1, elements.size() - 1)));
    }
}
