package edu.cmu.lti.oaqa.baseqa.eval.evaluatee;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.baseqa.eval.EvaluateeProvider;
import edu.cmu.lti.oaqa.ecd.config.ConfigurableProvider;
import edu.cmu.lti.oaqa.framework.ViewManager;
import edu.cmu.lti.oaqa.framework.ViewManager.ViewType;
import edu.cmu.lti.oaqa.type.answer.Answer;
import edu.cmu.lti.oaqa.util.TypeUtil;

public class AnswerEvaluateeProvider extends ConfigurableProvider implements
        EvaluateeProvider<Answer> {

  @Override
  public Collection<Answer> getGoldStandard(JCas jcas) throws CASException {
    return TypeUtil.getAnswers(ViewManager.getOrCreateView(jcas, ViewType.FINAL_ANSWER_GS));
  }

  @Override
  public Collection<Answer> getResults(JCas jcas) throws CASException {
    return TypeUtil.getAnswers(jcas);
  }

  @Override
  public Comparator<Answer> comparator() {
    return Comparator.comparing(Answer::getRank);
  }

  @Override
  public Function<Answer, String> uniqueIdMapper() {
    return Answer::getText;
  }

}
