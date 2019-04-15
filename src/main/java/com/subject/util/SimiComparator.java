package com.subject.util;

import com.subject.model.SubSim;

import java.util.Comparator;

public class SimiComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        SubSim t1=(SubSim)o1;
        SubSim t2=(SubSim)o2;
        return t2.getSimilard().compareTo(t1.getSimilard());
    }
}
