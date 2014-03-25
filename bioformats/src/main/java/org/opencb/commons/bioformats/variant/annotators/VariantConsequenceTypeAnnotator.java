package org.opencb.commons.bioformats.variant.annotators;

import com.google.common.base.Joiner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.opencb.biodata.models.variant.Variant;
import org.opencb.biodata.models.variant.effect.VariantEffect;
import org.opencb.biodata.tools.variant.EffectCalculator;

/**
 * @author Alejandro Aleman Ramos <aaleman@cipf.es>
 */
public class VariantConsequenceTypeAnnotator implements VariantAnnotator {


    @Override
    public void annot(List<Variant> batch) {

        List<VariantEffect> batchEffect = EffectCalculator.getEffects(batch);

        for (Variant variant : batch) {

            annotVariantEffect(variant, batchEffect);
        }

    }

    private void annotVariantEffect(Variant variant, List<VariantEffect> batchEffect) {

        Set<String> ct = new HashSet<>();
        for (VariantEffect effect : batchEffect) {

            if (variant.getChromosome().equals(effect.getChromosome()) &&
                    variant.getStart() == effect.getPosition() &&
                    variant.getReference().equals(effect.getReferenceAllele()) &&
                    variant.getAlternate().equals(effect.getAlternativeAllele())) {

                ct.add(effect.getConsequenceTypeObo());
            }

        }

        String ct_all = Joiner.on(",").join(ct);

        if (ct.size() > 0) {
//            variant.addInfoField("ConsType=" + ct_all);
            variant.addAttribute("ConsType", ct_all); // TODO aaleman: Check this code
        }

    }

    @Override
    public void annot(Variant elem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
