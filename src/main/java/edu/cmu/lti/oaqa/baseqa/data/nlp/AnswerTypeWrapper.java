package edu.cmu.lti.oaqa.baseqa.data.nlp;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.oaqa.model.core.OAQAAnnotation;
import org.oaqa.model.nlp.AnswerType;

import com.google.common.base.Objects;

import edu.cmu.lti.oaqa.baseqa.data.core.OAQAAnnotationWrapper;
import edu.cmu.lti.oaqa.baseqa.data.core.WrapperHelper;
import edu.cmu.lti.oaqa.baseqa.data.gerp.GerpAnnotationWrapper;

public class AnswerTypeWrapper extends GerpAnnotationWrapper<AnswerType> {

  private static final long serialVersionUID = 1L;

  private String label;

  private OAQAAnnotationWrapper<?> targetType;

  public AnswerTypeWrapper(int begin, int end, String label, OAQAAnnotationWrapper<?> targetType) {
    super(begin, end);
    this.label = label;
    this.targetType = targetType;
  }

  public AnswerTypeWrapper(int begin, int end, String label, OAQAAnnotationWrapper<?> targetType,
          String generator) {
    super(begin, end, generator);
    this.label = label;
    this.targetType = targetType;
  }

  @Override
  public Class<? extends AnswerType> getTypeClass() {
    return AnswerType.class;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public void wrap(AnswerType annotation) throws AnalysisEngineProcessException {
    super.wrap(annotation);
    this.label = annotation.getLabel();
    try {
      this.targetType = WrapperHelper.matchSubclassAndWrapIfNotWrapped(
              (OAQAAnnotation) annotation.getTargetType(),
              (Class<OAQAAnnotationWrapper<OAQAAnnotation>>) (Class) OAQAAnnotationWrapper.class);
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }

  @Override
  public AnswerType unwrap(JCas jcas) throws AnalysisEngineProcessException {
    AnswerType annotation = super.unwrap(jcas);
    annotation.setLabel(label);
    annotation.setTargetType(targetType.unwrapIfNotUnwrapped(jcas));
    return annotation;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(super.hashCode(), label);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    AnswerTypeWrapper other = (AnswerTypeWrapper) obj;
    return Objects.equal(this.label, other.label);
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public OAQAAnnotationWrapper<?> getTargetType() {
    return targetType;
  }

  public void setTargetType(OAQAAnnotationWrapper<?> targetType) {
    this.targetType = targetType;
  }

}