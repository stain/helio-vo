package ch.i4ds.helio
import ch.i4ds.helio.frontend.parser.*
import ch.i4ds.helio.frontend.query.*
import eu.heliovo.clientapi.frontend.*

class ResultVTManagerService {

    static transactional = true
    private List<ResultVT>  resultList =new LinkedList<ResultVT>();
    private List<String>  resultListServiceRefence =new LinkedList<String>();
    
    
    
    

    public int addResult(ResultVT r,String service){
        
        this.resultList.add(r);
        this.resultListServiceRefence.add(service);
        return this.resultList.size() -1;
    }
    public ResultVT getResult(int index){
        try{
            return this.resultList.get(index);
        }catch (Exception e){
            return null;
        }
    }
    public String getResultServiceReference(int index){
        try{
            return this.resultListServiceRefence.get(index);
        }catch (Exception e){
            return null;
        }
    }
    

}
