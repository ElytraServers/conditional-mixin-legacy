package me.fallenbreath.conditionalmixin.impl;

import java.lang.annotation.Annotation;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.util.Annotations;

import me.fallenbreath.conditionalmixin.api.mixin.AnnotationCleaner;

public class AnnotationCleanerImpl implements AnnotationCleaner {

    private final Class<? extends Annotation> annotationClass;

    public AnnotationCleanerImpl(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Nullable
    private AnnotationNode previousRestrictionAnnotation;

    @Override
    public void onPreApply(ClassNode targetClass) {
        this.previousRestrictionAnnotation = Annotations.getVisible(targetClass, this.annotationClass);
    }

    @Override
    public void onPostApply(ClassNode targetClass) {
        String descriptor = Type.getDescriptor(this.annotationClass);
        List<AnnotationNode> annotationNodes = targetClass.visibleAnnotations;
        if (annotationNodes == null) {
            return;
        }
        int index = -1;
        for (int i = 0; i < annotationNodes.size(); i++) {
            if (descriptor.equals(annotationNodes.get(i).desc)) {
                index = i;
                break;
            }
        }
        if (this.previousRestrictionAnnotation == null && index != -1) // @Restriction is merged, drop it
        {
            annotationNodes.remove(index);
        } else if (this.previousRestrictionAnnotation != null) // preserve the original @Restriction
        {
            annotationNodes.set(index, this.previousRestrictionAnnotation);
        }
    }
}
