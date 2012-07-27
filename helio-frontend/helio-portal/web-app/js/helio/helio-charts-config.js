/*
* Configuration file for the HELIOcharts project
*
* For every event catalogue, an object literal is defined.
* By calling getHELIOconfiguration(catalogue), the configuration
* object of the given catalogue is returned.
*
* The structure of an object is the following (only the supported plot
* types are listed in an actual object, e.g. 'scatter' and 'sun')
*
* object_catalogue_name
*                     |- plots
*                     |      |- names_of_all_available_plots
*                     |
*                     |- standardPlot
*                     |             |- name_of_standard_plot
*                     |
*                     |- scatter
*                     |        |- xAxis
*                     |        |      |- name_of_xAxis_values
*                     |        |
*                     |        |- xAxisDefault
*                     |        |      |- name_of_xAxis_default_value
*                     |        |
*                     |        |- yAxis
*                     |        |      |- name_of_yAxis_values
*                     |        |
*                     |        |- yAxisDefault
*                     |        |      |- name_of_yAxis_default_value
*                     |        |
*                     |        |- addInfo
*                     |               |- name_of_additional_information_values
*                     |
*                     |
*                     |...
*
* Author: Roman Boutellier
*/

/**
* Returns the requested configuration object.
* @param catalogue Name of the catalogue of which the configuration object is reqeusted (string)
* @return Object containing the names of all possible chart types as well as the according arrays containing the names of the data series
*/
function getHELIOconfiguration(catalogue) {
    // Eval is not a good option, therefore another way is used
    
    // Create the full function object name
    var objectName = 'object_';
    objectName += catalogue;
    // Create a function object
    var fn = window[objectName];
    
    // Check if the configuration object exists. Otherwise return the default object.
    if(fn == null){
        fn = window['object_default'];
    }
    
    // Return the requested configuration object
    return fn;
}

/**
* Default configuration object
*/
var object_default = {
    // Default plot is a scatter plot
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Default scatter plot
    scatter: {
        xAxis: [
            'time_start',
            'time_peak',
            'time_end'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        yAxis: [
            'aastar_ave',
            'aastar_max',
            'aastar_sum',
            'beta',
            'delta_v',
            'freq_start',
            'freq_end',
            'hxt_hi',
            'hxt_lo',
            'mag_ratio',
            'nar',
            'pt_max',
            'pt_max_sheath',
            'b_max',
            'b_max_sheath',
            'v',
            'v_cme',
            'v_init',
            'v_final',
            'v_20r',
            'v_max',
            'v_max_sheath',
            'lat_hg',
            'long_hg'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'Events'
        ],
        // additional information
        addInfo: [
            'hec_id'
        ]
    }
};

/**
* Configuration object for the catalogue 'aad_gle'
*/
var object_aad_gle = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // all values possible for the x-axis
        xAxis: [
            'time_start',
            'time_end'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'Events'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'Events'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'comment'
        ]
    }
};

/**
* Configuration object for the catalogue 'cactus_soho_cme'
*/
var object_cactus_soho_cme = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'v',
            'dv',
            'v_min',
            'v_max'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'v'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'sat_id',
            'r_hci',
            'lat_hci',
            'long_hci',
            'duration',
            'pa',
            'pa_width',
            'flag_halo',
            'cme_number'
        ]
    }
};

/**
* Configuration object for the catalogue 'cactus_soho_flow'
*/
var object_cactus_soho_flow = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'v',
            'dv',
            'v_min',
            'v_max'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'v'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'sat_id',
            'r_hci',
            'lat_hci',
            'long_hci',
            'duration',
            'pa',
            'pa_width',
            'flag_halo',
            'cme_number'
        ]
    }   
};

/**
* Configuration object for the catalogue 'cactus_stereoa_cme'
*/
var object_cactus_stereoa_cme = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'v',
            'dv',
            'v_min',
            'v_max'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'v'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'sat_id',
            'r_hci',
            'lat_hci',
            'long_hci',
            'duration',
            'pa',
            'pa_width',
            'flag_halo',
            'cme_number'
        ]
    }
};

/**
* Configuration object for the catalogue 'cactus_stereoa_flow'
*/
var object_cactus_stereoa_flow = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'v',
            'dv',
            'v_min',
            'v_max'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'v'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'sat_id',
            'r_hci',
            'lat_hci',
            'long_hci',
            'duration',
            'pa',
            'pa_width',
            'flag_halo',
            'cme_number'
        ]
    }
};

/**
* Configuration object for the catalogue 'cactus_stereob_cme'
*/
var object_cactus_stereob_cme = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'v',
            'dv',
            'v_min',
            'v_max'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'v'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'sat_id',
            'r_hci',
            'lat_hci',
            'long_hci',
            'duration',
            'pa',
            'pa_width',
            'flag_halo',
            'cme_number'
        ]
    }
};

/**
* Configuration object for the catalogue 'cactus_stereob_flow'
*/
var object_cactus_stereob_flow = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'v',
            'dv',
            'v_min',
            'v_max'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'v'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'sat_id',
            'r_hci',
            'lat_hci',
            'long_hci',
            'duration',
            'pa',
            'pa_width',
            'flag_halo',
            'cme_number'
        ]
    }
};

/**
* Configuration object for the catalogue 'stereo_impactplastic_icme'
*/
var object_stereo_impactplastic_icme = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start',
            'time_end',
            'time_mag_obstacle_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'pt_max',
            'pt_max_sheath',
            'b_max',
            'b_max_sheath',
            'v_max',
            'v_max_sheath',
            'delta_v',
            'spacecraft'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'pt_max'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'id',
            'flag',
            'spacecraft',
            'group',
            'comment'
        ],
        // information about how to categorize the y-axis when 'spacecraft' is plotted against it
        spacecraft:{
            // the values which the divided y-axis represents
            values: [
                'A',
                'B'
            ]
        }
    }
};

/**
* Configuration object for the catalogue 'stereoa_impactplastic_shock'
*/
var object_stereoa_impactplastic_shock = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'mag_ratio',
            'norm_angle',
            'beta',
            'mach_no'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'beta'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'id',
            'data_avail',
            'f_r_shock',
            'comment'
        ]
    }
};

/**
* Configuration object for the catalogue 'stereob_impactplastic_shock'
*/
var object_stereob_impactplastic_shock = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'mag_ratio',
            'norm_angle',
            'beta',
            'mach_no'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'beta'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'id',
            'data_avail',
            'f_r_shock',
            'comment'
        ]
    }   
};

/**
* Configuration object for the catalogue 'stereoa_impactplastic_sir'
*/
var object_stereoa_impactplastic_sir = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start',
            'time_end',
            'time_pt_max'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'pt_max',
            'b_max',
            'np_max',
            'v_min',
            'v_max'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'pt_max'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'comp_id',
            'id',
            'flag',
            'spacecraft'
        ]
    }
};

/**
* Configuration object for the catalogue 'stereob_impactplastic_sir'
*/
var object_stereob_impactplastic_sir = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start',
            'time_end',
            'time_pt_max'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'pt_max',
            'b_max',
            'np_max',
            'v_min',
            'v_max'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'pt_max'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'comp_id',
            'id',
            'flag',
            'spacecraft'
        ]
    }
};

/**
* Configuration object for the catalogue 'cme_forbush_event'
*/
var object_cme_forbush_event = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'fe_magn',
            'kpmax',
            'dst_min',
            'b_max',
            'vsw_max',
            'axy_max',
            'az_r',
            'tmin',
            'dc_min',
            'tdmn',
            'tdmn'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'kpmax'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'flag_ssc',
            'tilt'
        ]
    }   
};

/**
* Configuration object for the catalogue 'cme_inner_heliosphere'
*/
var object_cme_inner_heliosphere = {
    // Available plots
    plots: [
        'scatter'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // x-axis
        xAxis: [
            'time_start'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // y-axis
        yAxis: [
            'v_cme',
            'xray_class',
            'optical_class',
            'v_icme',
            'alpha',
            'v_1au_predict'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'v_cme'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'cme_number',
            'cme_type',
            'lat_hg',
            'long_hg',
            'long_carr',
            'time_ips_1au',
            'time_cme_1au',
            'time_transit_1au',
            'time_transit_1au_predict',
            'flag_cme_interaction'
        ]
    }   
};

/**
* Configuration object for the catalogue 'goes_sxr_flare'
*/
var object_goes_sxr_flare = {
    // Available plots
    plots: [
        'scatter',
        'column',
        'histogram'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // all values possible for the x-axis
        xAxis: [
            'time_start',
            'time_peak',
            'time_end'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // all values possible for the y-axis
        yAxis: [
            'nar',
            'lat_hg',
            'long_hg',
            'long_carr'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'nar'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'xray_class',
            'optical_class'
        ]
    },
    // column plot
    column: {
        // all values possible for the x-axis
        xAxis: [
            'time_start',
            'time_peak',
            'time_end'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // all values possible for the y-axis
        yAxis: [
            'nar',
            'lat_hg',
            'long_hg',
            'long_carr'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'nar'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'xray_class',
            'optical_class'
        ]
    },
    // Histogram plot
    histogram: {
        // the x-axis name
        xAxisName: 'xray_class',
        xAxisCategories: [
                        'C1 - C1.9',
                        'C2 - C2.9',
                        'C3 - C3.9',
                        'C4 - C4.9',
                        'C5 - C5.9',
                        'C6 - C6.9',
                        'C7 - C7.9',
                        'C8 - C8.9',
                        'C9 - C9.9',
                        'M1 - M1.9',
                        'M2 - M2.9',
                        'M3 - M3.9',
                        'M4 - M4.9',
                        'M5 - M5.9',
                        'M6 - M6.9',
                        'M7 - M7.9',
                        'M8 - M8.9',
                        '> M9'
        ],
        yAxisName: 'Number of flares within the according x-ray class',
        /**
        * This function gets a value and returns the correct category (out of
        * the array xAxisCategories).
        *
        * @param actualValue The value to check
        **/
        xFunction: function (actualValue) {
            // Check if the actualValue is not '-'/null/undefined
            if((actualValue != '-') && (actualValue != null) && actualValue != undefined) {
                // Split the value into the letter and the number
                var actualLetter = actualValue.slice(0,1);
                var actualNumber = actualValue.slice(1);
                // Check if the letter is 'C'
                if(actualLetter == 'C'){
                    // Check the number and return the correct category
                    if(actualNumber < 2){
                        return 'C1 - C1.9';
                    } else if(actualNumber < 3){
                        return 'C2 - C2.9';
                    } else if(actualNumber < 4){
                        return 'C3 - C3.9';
                    } else if(actualNumber < 5){
                        return 'C4 - C4.9';
                    } else if(actualNumber < 6){
                        return 'C5 - C5.9';
                    } else if(actualNumber < 7){
                        return 'C6 - C6.9';
                    } else if(actualNumber < 8){
                        return 'C7 - C7.9';
                    } else if(actualNumber < 9){
                        return 'C8 - C8.9';
                    } else if(actualNumber < 10){
                        return 'C9 - C9.9';
                    } else {
                        return '-';
                    }
                } else if(actualLetter == 'M'){ // The letter is 'M'
                    // Check the number and return the correct category
                    if(actualNumber < 2){
                        return 'M1 - M1.9';
                    } else if(actualNumber < 3){
                        return 'M2 - M2.9';
                    } else if(actualNumber < 4){
                        return 'M3 - M3.9';
                    } else if(actualNumber < 5){
                        return 'M4 - M4.9';
                    } else if(actualNumber < 6){
                        return 'M5 - M5.9';
                    } else if(actualNumber < 7){
                        return 'M6 - M6.9';
                    } else if(actualNumber < 8){
                        return 'M7 - M7.9';
                    } else if(actualNumber < 9){
                        return 'M8 - M8.9';
                    } else if(actualNumber < 10){
                        return 'M9 - M9.9';
                    } else {
                        return '> M9';
                    }
                } else { // The letter is neither 'C' nor 'M'
                    return '-';
                }
            } else { // The value is not present, so return '-'
                return '-';
            }
        }
    }
};

/**
* Configuration object for the catalogue 'goes_sxr_flare'
*/
var object_rhessi_hxr_flare = {
    // Available plots
    plots: [
        'scatter',
        'column',
        'histogram'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // all values possible for the x-axis
        xAxis: [
            'time_start',
            'time_peak',
            'time_end',
            'duration'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_peak'
        ],
        // all values possible for the y-axis
        yAxis: [
            'x_cart',
            'y_cart',
            'radial_arcsec',
            'duration',
            'count_sec_peak',
            'total_count',
        ],
        // default value for the y-axis
        yAxisDefault: [
            'total_count'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'nar',
            'energy_kev',
            'flare_number'
        ]
    },
    // column plot
    column: {
        // all values possible for the x-axis
        xAxis: [
            'time_start',
            'time_peak',
            'time_end'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_peak'
        ],
        // all values possible for the y-axis
        yAxis: [
            'x_cart',
            'y_cart',
            'radial_arcsec',
            'duration',
            'count_sec_peak',
            'total_count'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'total_count'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'nar',
            'energy_kev',
            'flare_number'
        ]
    },
    // Histogram plot
    histogram: {
        // the x-axis name
        xAxisName: 'xray_class',
        xAxisCategories: [
                        'C1 - C1.9',
                        'C2 - C2.9',
                        'C3 - C3.9',
                        'C4 - C4.9',
                        'C5 - C5.9',
                        'C6 - C6.9',
                        'C7 - C7.9',
                        'C8 - C8.9',
                        'C9 - C9.9',
                        'M1 - M1.9',
                        'M2 - M2.9',
                        'M3 - M3.9',
                        'M4 - M4.9',
                        'M5 - M5.9',
                        'M6 - M6.9',
                        'M7 - M7.9',
                        'M8 - M8.9',
                        '> M9'
        ],
        yAxisName: 'Number of flares within the according x-ray class',
        /**
        * This function gets a value and returns the correct category (out of
        * the array xAxisCategories).
        *
        * @param actualValue The value to check
        **/
        xFunction: function (actualValue) {
            // Check if the actualValue is not '-'/null/undefined
            if((actualValue != '-') && (actualValue != null) && actualValue != undefined) {
                // Split the value into the letter and the number
                var actualLetter = actualValue.slice(0,1);
                var actualNumber = actualValue.slice(1);
                // Check if the letter is 'C'
                if(actualLetter == 'C'){
                    // Check the number and return the correct category
                    if(actualNumber < 2){
                        return 'C1 - C1.9';
                    } else if(actualNumber < 3){
                        return 'C2 - C2.9';
                    } else if(actualNumber < 4){
                        return 'C3 - C3.9';
                    } else if(actualNumber < 5){
                        return 'C4 - C4.9';
                    } else if(actualNumber < 6){
                        return 'C5 - C5.9';
                    } else if(actualNumber < 7){
                        return 'C6 - C6.9';
                    } else if(actualNumber < 8){
                        return 'C7 - C7.9';
                    } else if(actualNumber < 9){
                        return 'C8 - C8.9';
                    } else if(actualNumber < 10){
                        return 'C9 - C9.9';
                    } else {
                        return '-';
                    }
                } else if(actualLetter == 'M'){ // The letter is 'M'
                    // Check the number and return the correct category
                    if(actualNumber < 2){
                        return 'M1 - M1.9';
                    } else if(actualNumber < 3){
                        return 'M2 - M2.9';
                    } else if(actualNumber < 4){
                        return 'M3 - M3.9';
                    } else if(actualNumber < 5){
                        return 'M4 - M4.9';
                    } else if(actualNumber < 6){
                        return 'M5 - M5.9';
                    } else if(actualNumber < 7){
                        return 'M6 - M6.9';
                    } else if(actualNumber < 8){
                        return 'M7 - M7.9';
                    } else if(actualNumber < 9){
                        return 'M8 - M8.9';
                    } else if(actualNumber < 10){
                        return 'M9 - M9.9';
                    } else {
                        return '> M9';
                    }
                } else { // The letter is neither 'C' nor 'M'
                    return '-';
                }
            } else { // The value is not present, so return '-'
                return '-';
            }
        }
    }
};

/**
* Configuration object for the catalogue 'yohkoh_hxr_flare'
*/
var object_yohkoh_hxr_flare = {
    // Available plots
    plots: [
        'scatter',
        'histogram'
    ],
    // Standard plot
    standardPlot: 'scatter',
    // Scatter plot
    scatter: {
        // all values possible for the x-axis
        xAxis: [
            'time_start',
            'time_peak',
            'time_end'
        ],
        // default value for the x-axis
        xAxisDefault: [
            'time_start'
        ],
        // all values possible for the y-axis
        yAxis: [
            'nar',
            'hxt_lo',
            'hxt_m1',
            'hxt_m2',
            'hxt_hi',
            'sxs_pc21',
            'sxs_pc22',
            'hxs_pc1',
            'hxs_pc2'
        ],
        // default value for the y-axis
        yAxisDefault: [
            'nar'
        ],
        // additional information
        addInfo: [
            'hec_id',
            'lat_hg',
            'long_hg',
            'long_carr',
            'xray_class',
            'optical_class',
            'x_lat_hg',
            'x_long_hg',
            'x_long_carr',
            'comment',
            'yoh_event'
        ]
    },
    // Histogram plot
    histogram: {
        // the x-axis name
        xAxisName: 'xray_class',
        xAxisCategories: [
                        'C1 - C1.9',
                        'C2 - C2.9',
                        'C3 - C3.9',
                        'C4 - C4.9',
                        'C5 - C5.9',
                        'C6 - C6.9',
                        'C7 - C7.9',
                        'C8 - C8.9',
                        'C9 - C9.9',
                        'M1 - M1.9',
                        'M2 - M2.9',
                        'M3 - M3.9',
                        'M4 - M4.9',
                        'M5 - M5.9',
                        'M6 - M6.9',
                        'M7 - M7.9',
                        'M8 - M8.9',
                        '> M9'
        ],
        yAxisName: 'Number of flares within the according x-ray class',
        /**
        * This function gets a value and returns the correct category (out of
        * the array xAxisCategories).
        *
        * @param actualValue The value to check
        **/
        xFunction: function (actualValue) {
            // Check if the actualValue is not '-'/null/undefined
            if((actualValue != '-') && (actualValue != null) && actualValue != undefined) {
                // Split the value into the letter and the number
                var actualLetter = actualValue.slice(0,1);
                var actualNumber = actualValue.slice(1);
                // Check if the letter is 'C'
                if(actualLetter == 'C'){
                    // Check the number and return the correct category
                    if(actualNumber < 2){
                        return 'C1 - C1.9';
                    } else if(actualNumber < 3){
                        return 'C2 - C2.9';
                    } else if(actualNumber < 4){
                        return 'C3 - C3.9';
                    } else if(actualNumber < 5){
                        return 'C4 - C4.9';
                    } else if(actualNumber < 6){
                        return 'C5 - C5.9';
                    } else if(actualNumber < 7){
                        return 'C6 - C6.9';
                    } else if(actualNumber < 8){
                        return 'C7 - C7.9';
                    } else if(actualNumber < 9){
                        return 'C8 - C8.9';
                    } else if(actualNumber < 10){
                        return 'C9 - C9.9';
                    } else {
                        return '-';
                    }
                } else if(actualLetter == 'M'){ // The letter is 'M'
                    // Check the number and return the correct category
                    if(actualNumber < 2){
                        return 'M1 - M1.9';
                    } else if(actualNumber < 3){
                        return 'M2 - M2.9';
                    } else if(actualNumber < 4){
                        return 'M3 - M3.9';
                    } else if(actualNumber < 5){
                        return 'M4 - M4.9';
                    } else if(actualNumber < 6){
                        return 'M5 - M5.9';
                    } else if(actualNumber < 7){
                        return 'M6 - M6.9';
                    } else if(actualNumber < 8){
                        return 'M7 - M7.9';
                    } else if(actualNumber < 9){
                        return 'M8 - M8.9';
                    } else if(actualNumber < 10){
                        return 'M9 - M9.9';
                    } else {
                        return '> M9';
                    }
                } else { // The letter is neither 'C' nor 'M'
                    return '-';
                }
            } else { // The value is not present, so return '-'
                return '-';
            }
        }
    }
};


/***************************************************************************************
************************ 'Prototype' of the Object *************************************
***************************************************************************************/

/**
* Configuration object for the catalogue ''
*/
var object_ = {
    // Available plots
    plots: [
    
    ],
    // Standard plot
    standardPlot: '',
    // Scatter plot
    scatter: {
        // all values possible for the x-axis
        xAxis: [
        
        ],
        // default value for the x-axis
        xAxisDefault: [
        
        ],
        // all values possible for the y-axis
        yAxis: [
            
        ],
        // default value for the y-axis
        yAxisDefault: [
        
        ],
        // additional information
        addInfo: [
            
        ]
    },
    // column plot
    column: {
        // all values possible for the x-axis
        xAxis: [
        
        ],
        // default value for the x-axis
        xAxisDefault: [
        
        ],
        // all values possible for the y-axis
        yAxis: [
            
        ],
        // default value for the y-axis
        yAxisDefault: [
        
        ],
        // additional information
        addInfo: [
            
        ]
    },
    // Histogram plot
    histogram: {
        // the x-axis name
        xAxisName: '',
        xAxisCategories: [
        ],
        yAxisName: '',
        /**
        * This function gets a value and returns the correct category (out of
        * the array xAxisCategories).
        *
        * @param actualValue The value to check
        **/
        xFunction: function (actualValue) {
        }
    }
};