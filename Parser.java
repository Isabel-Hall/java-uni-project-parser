import computation.contextfreegrammar.*;
import computation.parser.*;
import computation.parsetree.*;
import computation.derivation.*;
import java.util.ArrayList;


public class Parser implements IParser {
  private int wordLength;
  private int numOfSteps;
  private ArrayList<Derivation> arrayOfDerivations = new ArrayList<Derivation>();
  private Variable startVariable;
  private Word startWord;
  private Derivation d;
  private int stepsRemaining;
  private Derivation solution;
  private ParseTreeNode childNode;
  private ParseTreeNode parentNode;
  private ParseTreeNode secondChildNode;
  private ParseTreeNode firstChildNode;

  public ArrayList<Derivation> removeTooLong(ArrayList<Derivation> array, int stepsRemaining){
    //Takes an arraylist of derivations and the number of steps remaining to generate the input word
    //Returns an arraylist of derivations and removes any that will take too many steps to generate a word
    ArrayList<Derivation> copyArray = new ArrayList<Derivation>();
    //goes through derivations in array (array.get(i) is the derivation)
    for(int i = 0; i < array.size(); i++){
      int varCount = 0;
      Word currentWord = array.get(i).getLatestWord();
      for(Symbol symbol : currentWord){
        if(!symbol.isTerminal()){
          varCount++;
        }
      }
      if(varCount <= stepsRemaining){
        copyArray.add(array.get(i));
      }
    }
    return copyArray;
  }
  
  public ArrayList<Derivation> buildDerivations(ArrayList<Derivation> array, ContextFreeGrammar cfg){
    //Takes an arraylist of derivations and returns a new list with all the possible rules applied to the first variable in the latest word (creating leftmost derivation)
    ArrayList<Derivation> copyArray = new ArrayList<Derivation>();
    for(int i = 0; i < array.size(); i++){
      Word latestWord = array.get(i).getLatestWord();
      //go through latestWord and find the first instance of a Variable
      for(int j = 0; j < latestWord.length(); j++){
        if(!latestWord.get(j).isTerminal()){
          //Find rules that apply to that variable
          for(Rule rule : cfg.getRules()){
            if(rule.getVariable().equals(latestWord.get(j))){
              Derivation extended = new Derivation(array.get(i));
              Word replacementWord = latestWord.replace(j, rule.getExpansion());
              extended.addStep(replacementWord, rule, j);
              copyArray.add(extended);
            }
          }
          break; //breaks out of for loop for latestWord after first symbol encountered to create leftmost derivation
        }
      }      
    }
    return copyArray;
  }

  private ParseTreeNode generateEmptyParseTree(ContextFreeGrammar cfg){
    if(emptyWordPresent(cfg)){
      return ParseTreeNode.emptyParseTree(cfg.getStartVariable());
    }
    return null;
  }

  private boolean emptyWordPresent(ContextFreeGrammar cfg){
    for(Rule rule : cfg.getRules()){
      if(rule.getExpansion().equals(Word.emptyWord)){
        return true;
      }
    }
    return false;
  }
  
  public boolean isInLanguage(ContextFreeGrammar cfg, Word w){
    wordLength = w.length();
    //If work is empty checks if empty word is accepted by the grammar
    if(wordLength == 0){
      return emptyWordPresent(cfg);
    }
    numOfSteps = (wordLength * 2) - 1;
    stepsRemaining = numOfSteps;
    startVariable = cfg.getStartVariable();
    startWord = new Word(startVariable);
    d = new Derivation(startWord);
    arrayOfDerivations.add(d);
    //While loop creates list of possible derivations
    while(stepsRemaining > 0){
      arrayOfDerivations = buildDerivations(arrayOfDerivations, cfg);
      arrayOfDerivations = removeTooLong(arrayOfDerivations, stepsRemaining);
      stepsRemaining--;

    }
    //Checks if input word is present in final list of derivations
    for(Derivation der : arrayOfDerivations){
      if(der.getLatestWord().equals(w)){
        return true;
      } 
    }    
    return false;
  }

  public ParseTreeNode generateParseTree(ContextFreeGrammar cfg, Word w) {
    boolean firstFree = true;
    boolean secondFree = true;
    boolean newSolution = false;
    ArrayList<ParseTreeNode> listOfNodes = new ArrayList<ParseTreeNode>();
    ArrayList<Step> steps = new ArrayList<Step>();
    wordLength = w.length();
    numOfSteps = (wordLength * 2) - 1;
    stepsRemaining = numOfSteps;
    //Generates empty parse tree if input word is the empty word and this is accepted by the grammar
    if(wordLength == 0){
      if(emptyWordPresent(cfg)){
        return generateEmptyParseTree(cfg);
      }
    }    
    startVariable = cfg.getStartVariable();
    startWord = new Word(startVariable);
    d = new Derivation(startWord);
    arrayOfDerivations.add(d);
    //Creates the list of derivations in the same way as isInLanguage
    while(stepsRemaining > 0){
      arrayOfDerivations = buildDerivations(arrayOfDerivations, cfg);
      arrayOfDerivations = removeTooLong(arrayOfDerivations, stepsRemaining);
      stepsRemaining--;
    }
    for(Derivation der : arrayOfDerivations){
      if(der.getLatestWord().equals(w)){
        solution = der;
        newSolution = true;
      } 
    }
    //Returns null if word not in grammar
    if(!newSolution){
      return null;
    }
    try {
      //makes a new arraylist of the steps in the reverse order that can be indexed
      for(Step step : solution){
        steps.add(step);
      }
      //Creates nodes for parse tree by going through each step in the derivation
      for(int i = 0; i < steps.size() - 1; i++){
        Rule currentRule = steps.get(i).getRule();
        Word expansion = currentRule.getExpansion();
        if(expansion.isTerminal()){
          childNode = new ParseTreeNode(expansion.get(0));
          parentNode = new ParseTreeNode(currentRule.getVariable(), childNode);
          listOfNodes.add(parentNode);
        } else {
          //The first and second symbols in the word expansion (made up of 2 variables)
          Symbol first = expansion.get(0);
          Symbol second = expansion.get(1);
          int[] nodesToRemove = new int[2];
          //Iterates back from the end of the listOfNodes to find matching symbols
          for(int j = listOfNodes.size() - 1; j >= 0; j--){
            if(listOfNodes.get(j).getSymbol().equals(first) && firstFree){
              firstChildNode = listOfNodes.get(j);
              firstFree = false;
              nodesToRemove[0] = j;
            }
            else if(listOfNodes.get(j).getSymbol().equals(second) && secondFree){
              secondChildNode = listOfNodes.get(j);
              secondFree = false;
              nodesToRemove[1] = j;
            }
          }
          parentNode = new ParseTreeNode(currentRule.getVariable(), firstChildNode, secondChildNode);
          listOfNodes.add(parentNode); 
          //remove the two nodes that were combined into the parent node to prevent the wrong nodes being added later on.
          listOfNodes.remove(Math.max(nodesToRemove[0], nodesToRemove[1]));
          listOfNodes.remove(Math.min(nodesToRemove[0], nodesToRemove[1]));
          firstFree = true;
          secondFree = true;    
        }        
      }
      return listOfNodes.get(listOfNodes.size() - 1);
    }
    catch(NullPointerException e){
      return null;
    }
  }
}
