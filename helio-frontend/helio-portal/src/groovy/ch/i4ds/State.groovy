/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.i4ds
import ch.ResultVT;
/**
 *
 * @author David
 */
class State {


    //public Stack<Stack<String>> r1
    public ResultVT r1;

    public ResultVT r2;
    public ResultVT r3;

    public LinkedList<String> q1;
    public String q2;
    public LinkedList<String> startDateQ2;
    public LinkedList<String> endDateQ2;
    public String q3;
    public LinkedList<String> instListQ3;
    public String q4;

    public int step;

    int getStep(){
        return step;
    }
    void setStep(String stepRequest){
        step =Integer.parseInt(stepRequest);
    }
    String getQClass(int question){
      //  System.out.println("Step = "+ step + " Question = "+question);
        if(step == question)return "questionReady";
        if(step < question)return "questionNotReady";
        if(step > question)return "questionDone";
    }

    State(){
        step =0;
    }

    /**
    String toString(){
        return this.q1 +this.q2+this.q3

    }
    **/

    Boolean isQ1(){
        if(q1 ==null)return false;
        return true;
    }
    Boolean isQ2(){
        if(q2 ==null)return false;
        return true;
    }
     Boolean isQ3(){
        if(q3 ==null)return false;
        return true;
    }
    Boolean isQ4(){
        if(q3 ==null)return false;
        return true;
    }



}

