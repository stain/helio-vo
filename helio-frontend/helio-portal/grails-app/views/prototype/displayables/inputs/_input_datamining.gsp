
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
            <select>
              <option> </option>
              <option>ACE</option>
              <option>WIND</option>
              <option>Ulysses</option>
              <option>STEREO-A</option>
              <option>STEREO-B</option>
            </select>
          </td>
          </tr>
          <tr>
          <td>
            <label>Instrument</label>
            <select>
              <option> </option>
              <option> SWEPAM</option>
              <option> MAG</option>
              <option> SWE</option>
              <option> MFI</option>
              <option> SWOOPS</option>
              <option> FGM/VHM</option>
              <option> PLASTIC</option>
            </select>
          </td>
            </tr>
          <tr>
          <td>
            <label>Measurement Type:</label>
            <select>
              <option> </option>
              <option> thermal plasma </option>
              <option> magnetic field </option>

            </select>
          </td>
            </tr>
          <tr>
           <td>
            <label>Function:</label>
            <select>
            <option>Deriv</option>
            <option>Sign Change</option>
            <option>Value</option>
            </select>
            (<input type="text" value="delta F"/>,<input type="text" value="average time"/>)
          </td>
          
        </tr>
      </table>
    </td>
   
  </tr><tr>
    <td></td>
    <td style="margin-top: 15px">
      <label>Expression: </label> <b>ACE.DERIV,V: > 100:600;WIND.DERIV,V: < 150:600;</b>

    </td>
    
  </tr>
  </table>
 
