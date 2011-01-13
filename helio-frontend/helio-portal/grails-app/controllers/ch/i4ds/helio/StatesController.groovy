package ch.i4ds.helio;
import ch.ResultVT;
import ch.i4ds.State;
import ch.i4ds.helio.frontend.query.WebServiceQuery;
import net.ivoa.xml.votable.v1.*;
import ch.i4ds.helio.frontend.parser.*;
import ch.i4ds.helio.frontend.query.*;
import org.w3c.dom.html.*;

class StatesController {

    def FrontHECService;
    def FrontICSService;
    def FrontDPASService;

    def DataQueryService;



    def downloadVOTable={

        /*enable for debug
        println session
        println params
        println session.state.getStep();
        println "step arriba"*/

        if(session.state ==null){
        

        }else if(session.state.getStep()== 2){
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=mytest.xml")
            response.outputStream << session.state.r1.getStringTable()
        
        }else if(session.state.getStep()== 3){
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=mytest.xml")
            response.outputStream << session.state.r2.getStringTable()
        
        }else if(session.state.getStep()== 4){
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=mytest.xml")
            response.outputStream << session.state.r3.getStringTable()
        }
    }
    def saveFits={
     
        if(session.state ==null){

        }else{
            for(Stack stack:session.state.r3.getStack()){
                for(int i = 0; i<stack.size();i++){
                    if(i==1)render(stack.get(i)+"\n");
                }
            }
        }

    }
    def workflow0 ={

        State state;
        if(session.state ==null){
            state = new State();
            session.state= state;
            state.setStep("1");

        }else {

            state = (State)session.state;
            state.setStep("1");

        }
        [state:state]

    }
    def workflow1 = {
        println "entre a workflow1"
        println params

        println session;
        State state = session.state;

        if(params.minDate !=null &&params.maxDate != null){
            WebServiceQuery query = new WebServiceQuery();
            bindData(query, params);

            LinkedList<String> maxDateList= new LinkedList<String>(); // initialize lists for webservice request
            LinkedList<String> minDateList= new LinkedList<String>();
            LinkedList<String> extraList = new LinkedList<String>(); // extra list stands for FROM parameter however from can be ambiguous within the gsp
            Date minDate = Date.parse("yyyy-MM-dd/HH:mm",query.getMinDate()+"/"+params.minTime);
            Date maxDate = Date.parse("yyyy-MM-dd/HH:mm",query.getMaxDate()+"/"+params.maxTime);
            String extra = params.extra;
            String addressPort = PortDirectory.class.getField(query.getServiceName()).get(String);
            println addressPort
            println maxDate.format("yyyy-MM-dd'T'HH:mm:ss")
            println minDate.format("yyyy-MM-dd'T'HH:mm:ss")
            // String startDate = params.startDate_month+"-"+params.startDate_day+"-"+params.startDate_year;
            // String endDate = params.endDate_month+"-"+params.endDate_day+"-"+params.endDate_year;
            maxDateList.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            minDateList.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            extraList.add(query.getExtra());
            LinkedList<String> dates = new LinkedList<String>();
            dates.add(minDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            dates.add(maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
            println "porque sale true"
            println dates
            state.q1 = dates;
            state.r1 = DataQueryService.queryService(minDateList,maxDateList,extraList,addressPort);
            //state.r1 = FrontHECService.queryService(minDate.format("yyyy-MM-dd'T'HH:mm:ss"),maxDate.format("yyyy-MM-dd'T'HH:mm:ss"));
        }
        state.setStep("2");
        session.state =state;
        println "aqui ando"
        [state:state]

    }

    def index = {
        redirect(action: "workflow0")

    }
    def next = {
        println "entre a next";
        println params;
        println session;
        if(session.state !=null){
            State state = session.state;
            state.setStep(params.step);
            session.state =state;
        }
        //redirect(action: "index")
    }





    def workflow2 ={
        println "entre a workflow2"
        println params

        WebServiceQuery query = new WebServiceQuery();
        bindData(query, params);
        State state = session.state;
        //   println query.getData();
        if(query.getData()?.equals("")){
            flash.message = "Please select one or more SOLAR EVENTS before proceding"
            redirect(action: "workflow1")
        }else if(query.getData()==null){
            println "entre al else magico de workflow 2";
            state.setStep("3");
        }else{

            String[] fields =query.getData().split(",");

            Stack<Stack<String>> stack =state.r1.getStack();
            Stack<Stack<String>> tempStack = new Stack<Stack<String>>();
            LinkedList<String> startDate=new LinkedList<Integer>();
            LinkedList<String> endDate=new LinkedList<Integer>();
            for(String field:fields){
                int temp= Integer.parseInt(field);
                tempStack.add(stack.get(temp));
                //startDate.add(stack.get(temp).get(1));
                //endDate.add(stack.get(temp).get(5));
                startDate.add(stack.get(temp).get(state.r1.startTime));
                endDate.add(stack.get(temp).get(state.r1.endTime));
            }
            // state.r1.setStack(tempStack);
            // state.r2 = FrontICSService.queryServiceFake(startDate,endDate);
            String temp1 =state.q1.get(0);
            String temp2 =state.q1.get(1);
            LinkedList<String> maxDateList= new LinkedList<String>(); // initialize lists for webservice request
            LinkedList<String> minDateList= new LinkedList<String>();
            LinkedList<String> extraList = new LinkedList<String>(); // extra list stands for FROM parameter however from can be ambiguous within the gsp
            String extra = params.extra;
            extraList.add("instrument");
            String addressPort = PortDirectory.class.getField("ICS").get(String);
            minDateList.add(temp1);
            maxDateList.add(temp2);

            state.r2 = DataQueryService.queryService(minDateList,maxDateList,extraList,addressPort);
            //state.r2 = FrontICSService.queryService(temp1,temp2);
            state.q2 = "done";
            state.startDateQ2 = startDate;
            state.endDateQ2 = endDate;
            //state.r2 = FrontICSService.queryService();
            // state.r2 = FrontICSService.queryServiceFake();
            session.state =state;
            state.setStep("3");


        }
        [state:state]
    }
    def workflow3 ={
        println "entre a q3"
        println params
        WebServiceQuery query = new WebServiceQuery();
        bindData(query, params);
        State state = session.state;
        println params;
        if(query.getData()?.equals("")){
            flash.message = "Please select one or more INSTRUMENTS before proceding"
            redirect(action: "workflow2")
        }else if(query.getData()==null){
            println "entre al else magico";
            state.setStep("4");
        }
        else{
            println query.getData();
            String[] fields =query.getData().split(",");

            Stack<Stack<String>> stack =state.r2.getStack();
            Stack<Stack<String>> tempStack = new Stack<Stack<String>>();



            println "antes del list";
            LinkedList<String> instList=new LinkedList<String>();

            for(String field:fields){
                int temp= Integer.parseInt(field);
                tempStack.add(stack.get(temp));
                instList.add(stack.get(temp).get(2));

            }
            //state.r2.setStack(tempStack);
            state.q3 = "listo";
            state.instListQ3=instList;
            ResultVT tempResult = new ResultVT();
            //state.r3 = FrontDPASService.queryService(state.startDateQ2,state.endDateQ2,instList);
            String addressPort = PortDirectory.class.getField("DPAS").get(String);
            println "antes del for";
            /* for(int i = 0;i< instList.size();i++){

            LinkedList<String> tempList = new LinkedList<String>();
            for(int j = 0; j < state.startDateQ2.size();j++  ){

            tempList.add(instList.get(i));
            }
            tempResult.addTable(DataQueryService.queryService(state.startDateQ2,state.endDateQ2,tempList,addressPort).res);

            }*/
            state.r3=DataQueryService.queryService2(state.startDateQ2,state.endDateQ2,instList,addressPort,"");
            println "acabe los adds"
            //state.r3 = tempResult;
            
            session.state =state;
            state.setStep("4");


        }
        [state:state]
    }

    def clean = {

        session.state = null;
        // redirect(action: "index")
        redirect(action: "workflow0")

    }
}
