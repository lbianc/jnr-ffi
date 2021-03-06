package jnr.ffi.util;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Utilities for collections of annotations
 */
public final class Annotations {
    public static final Collection<Annotation> EMPTY_ANNOTATIONS = Collections.emptyList();

    private Annotations() {}

    public static Collection<Annotation> sortedAnnotationCollection(Annotation[] annotations) {
        if (annotations.length > 1) {
            return sortedAnnotationCollection(Arrays.asList(annotations));

        } else if (annotations.length > 0) {
            return Collections.singletonList(annotations[0]);

        } else {
            return Collections.emptyList();
        }
    }

    public static Collection<Annotation> sortedAnnotationCollection(Collection<Annotation> annotations) {
        // If already sorted, or empty, or only one element, no need to sort again
        if (annotations.size() < 2 || (annotations instanceof SortedSet && ((SortedSet) annotations).comparator() instanceof AnnotationNameComparator)) {
            return annotations;
        }

        SortedSet<Annotation> sorted = new TreeSet<Annotation>(AnnotationNameComparator.getInstance());
        sorted.addAll(annotations);

        return Collections.unmodifiableSortedSet(sorted);
    }

    public static final Collection<Annotation> mergeAnnotations(Collection<Annotation> a, Collection<Annotation> b) {
        if (a.isEmpty() && b.isEmpty()) {
            return EMPTY_ANNOTATIONS;

        } else if (!a.isEmpty() && b.isEmpty()) {
            return a;

        } else if (a.isEmpty() && !b.isEmpty()) {
            return b;

        } else {
            List<Annotation> all = new ArrayList<Annotation>(a);
            all.addAll(b);
            return sortedAnnotationCollection(all);
        }
    }

    public static final Collection<Annotation> mergeAnnotations(Collection<Annotation>... collections) {
        int totalLength = 0;
        for (Collection<Annotation> c : collections) {
            totalLength += c.size();
        }

        List<Annotation> all = new ArrayList<Annotation>(totalLength);
        for (Collection<Annotation> c : collections) {
            all.addAll(c);
        }
        
        return sortedAnnotationCollection(all);
    }
}
