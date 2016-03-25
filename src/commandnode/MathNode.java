package commandnode;

import java.util.List;

import exception.SLogoException;
import model.SLogoCharacterState;

public abstract class MathNode extends BinaryNode{

	private static final int SUM_OPCODE = 0;
	private static final int DIFFERENCE_OPCODE = 1;
	private static final int QUOTIENT_OPCODE = 2;
	private static final int PRODUCT_OPCODE = 3;

	public double evaluate(SLogoCharacterState state, int opcode) throws SLogoException {

		double childZeroEvaluation = evaluateChild(0, state);
		double childOneEvaluation = evaluateChild(1, state);

		for(int i=0; i<=1; i++){
			if(getChild(i) instanceof ParenthesisNode){
				List<Double> listEvaluations = ((ListNode) getChild(i)).getEvaluations();
				for(int x=0; x<listEvaluations.size(); x++){
					double innerChildEvaluation = listEvaluations.get(x);
					if(i==0){
						if(x==0){
							if(opcode == SUM_OPCODE || opcode == DIFFERENCE_OPCODE)
								childZeroEvaluation = 0;
							else if(opcode == QUOTIENT_OPCODE || opcode == PRODUCT_OPCODE)
								childZeroEvaluation = 1;
						}
						if(opcode == SUM_OPCODE)
							childZeroEvaluation += innerChildEvaluation;
						else if(opcode == DIFFERENCE_OPCODE)
							childZeroEvaluation -= innerChildEvaluation;
						else if(opcode == QUOTIENT_OPCODE)
							childZeroEvaluation /= innerChildEvaluation;
						else if(opcode == PRODUCT_OPCODE)
							childZeroEvaluation *= innerChildEvaluation;
					}
					else{
						if(x==0){
							if(opcode == SUM_OPCODE || opcode == DIFFERENCE_OPCODE)
								childOneEvaluation = 0;
							else if(opcode == QUOTIENT_OPCODE || opcode == PRODUCT_OPCODE)
								childOneEvaluation = 1;
						}
						if(opcode == SUM_OPCODE)
							childOneEvaluation += innerChildEvaluation;
						else if(opcode == DIFFERENCE_OPCODE)
							childOneEvaluation -= innerChildEvaluation;
						else if(opcode == QUOTIENT_OPCODE)
							childOneEvaluation /= innerChildEvaluation;
						else if(opcode == PRODUCT_OPCODE)
							childOneEvaluation *= innerChildEvaluation;
					}
				}
			}
		}
		if(opcode == SUM_OPCODE)
			return childZeroEvaluation + childOneEvaluation;
		else if(opcode == DIFFERENCE_OPCODE)
			return childZeroEvaluation - childOneEvaluation;
		else if(opcode == QUOTIENT_OPCODE)
			return childZeroEvaluation / childOneEvaluation;
		else
			return childZeroEvaluation * childOneEvaluation;
	}

}