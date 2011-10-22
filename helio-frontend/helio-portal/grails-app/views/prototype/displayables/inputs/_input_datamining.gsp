
<table style="width: 100%;border-bottom: 1px solid black" class="input_form_table">
  
  <tr  >
    <td style="vertical-align:middle;" >
      
<img class="block_img" style=" padding: 0; float: left; margin: 10px;" src="${resource(dir:'images/helio',file:'block.png')}" />
      
    </td>
    <td>
      <table>
        <tr>
          <td>
            <label>Mission:</label>
            <select id="input_mission">
              <option> </option>
              <option value="ACE">ACE</option>
              <option value="WIND">WIND</option>
              <option value="ULYSSES">Ulysses</option>
              <option value="STA">STEREO-A</option>
              <option value="STB">STEREO-B</option>
            </select>
          </td>
          </tr>
          <tr>
          <td>
            <label>Instrument</label>
            <select id="input_instrument">
              <option> </option>
      
            </select>
          </td>
            </tr>
          <tr>
          <td>
            <label>Measurement Type:</label>
            <input id="input_measurement" type="text"/>
          
          </td>
            </tr>
          <tr>
           <td>
            <label>Function:</label>

            <select id="input_function">
           
            </select>
            
          </td>
          
        </tr>
         <tr>
           <td>
             (Argument: 
             <select id="input_argument">
               <option> </option>
             </select>
             <select id="input_operator">
             <option><</option>
             <option>></option>
             <option>=</option>
             </select>
             <input id="input_condition" size="5"  type="text" value=""/>
             ,Average Time<input id="input_average_time" size="5" type="text" value=""/>,Time Window<input  id="input_time_window" size="5" type="text" value=""/>)
           </td>
          
        </tr>
      </table>
    </td>
   
  </tr><tr>
    <td></td>
    <td style="margin-top: 15px">
      <label>Expression: </label> <b><span id="input_expression"> 150:600;</span></b>

    </td>
    
  </tr>
  </table>
 
