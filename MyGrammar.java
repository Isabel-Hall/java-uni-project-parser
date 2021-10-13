import computation.contextfreegrammar.*;
import java.util.List;
import java.util.ArrayList;

public class MyGrammar {
	public static ContextFreeGrammar makeGrammar() {
		// You can write your code here to make the context-free grammar from the assignment
    Variable S0 = new Variable("S0");
    Variable S = new Variable('S');
    Variable T = new Variable('T');
    Variable F = new Variable('F');
    Variable P = new Variable('P');
    Variable Q = new Variable('Q');
    Variable R = new Variable('R');
    Variable A = new Variable('A');
    Variable B = new Variable('B');
    Variable C = new Variable('C');
    Variable D = new Variable('D');

    Terminal x = new Terminal('x');
    Terminal plus = new Terminal('+');
    Terminal mult = new Terminal('*');
    Terminal one = new Terminal('1');
    Terminal zero = new Terminal('0');
    Terminal left = new Terminal('(');
    Terminal right = new Terminal(')');

    Rule r1 = new Rule(S0, new Word(S, P));
    Rule r2 = new Rule(S0, new Word(T, Q));
    Rule r3 = new Rule(S0, new Word(A, R));
    Rule r4 = new Rule(S0, new Word(one));
    Rule r5 = new Rule(S0, new Word(zero));
    Rule r6 = new Rule(S0, new Word(x));

    Rule r7 = new Rule(S, new Word(S, P));
    Rule r8 = new Rule(S, new Word(T, Q));
    Rule r9 = new Rule(S, new Word(A, R));
    Rule r10 = new Rule(S, new Word(one));
    Rule r11 = new Rule(S, new Word(zero));
    Rule r12 = new Rule(S, new Word(x));

    Rule r13 = new Rule(T, new Word(T, Q));
    Rule r14 = new Rule(T, new Word(A, R));
    Rule r15 = new Rule(T, new Word(one));
    Rule r16 = new Rule(T, new Word(zero));
    Rule r17 = new Rule(T, new Word(x));

    Rule r18 = new Rule(F, new Word(A, R));
    Rule r19 = new Rule(F, new Word(one));
    Rule r20 = new Rule(F, new Word(zero));
    Rule r21 = new Rule(F, new Word(x));

    Rule r22 = new Rule(P, new Word(B, T));
    Rule r23 = new Rule(Q, new Word(C, F));
    Rule r24 = new Rule(R, new Word(S, D));
    Rule r25 = new Rule(A, new Word(left));
    Rule r26 = new Rule(B, new Word(plus));
    Rule r27 = new Rule(C, new Word(mult));
    Rule r28 = new Rule(D, new Word(right));

    List<Rule> rules = new ArrayList<Rule>();
    rules.add(r1);
    rules.add(r2);
    rules.add(r3);
    rules.add(r4);
    rules.add(r5);
    rules.add(r6);
    rules.add(r7);
    rules.add(r8);
    rules.add(r9);
    rules.add(r10);
    rules.add(r11);
    rules.add(r12);
    rules.add(r13);
    rules.add(r14);
    rules.add(r15);
    rules.add(r16);
    rules.add(r17);
    rules.add(r18);
    rules.add(r19);
    rules.add(r20);
    rules.add(r21);
    rules.add(r22);
    rules.add(r23);
    rules.add(r24);
    rules.add(r25);
    rules.add(r26);
    rules.add(r27);
    rules.add(r28);
    ContextFreeGrammar cfg = new ContextFreeGrammar(rules);

		return cfg;
	}
}
