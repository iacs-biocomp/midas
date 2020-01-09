/**
 * BIGAN_COLORS
 */

/**
 * biganColors: Object to define common color schemes in Bigan
 */
var biganColors = {
  qualitative: [
    '#65B32E',
    '#7CBDC4',
    '#C0D236',
    '#3E5B84',
    '#008C75',
    '#82428D',
    '#E8683F',
    '#B81A5D'
  ],
  positive: [
    '#0C4828',
    '#1A6E31',
    '#207732',
    '#208135',
    '#289337',
    '#429E35',
    '#65B32E',
    '#89BE47',
    '#9CC65A',
    '#B2CF6E',
    '#C0D47A',
    '#C9D985',
    '#E7E7B9'
  ],
  neutral: [
    '#003C50',
    '#1A6B85',
    '#27758E',
    '#3C8EA2',
    '#4999AB',
    '#5FA7B5',
    '#7CBDC4',
    '#93C7CF',
    '#A5CED7',
    '#ADD2DD',
    '#BBD8E5',
    '#C2DAE8',
    '#E3E8F0'
  ],
  negative: [
    '#7C170F',
    '#A82D17',
    '#AE3417',
    '#B63D17',
    '#C34A17',
    '#C74F1B',
    '#CC6B21',
    '#D6852B',
    '#DC9635',
    '#E1A744',
    '#E6B04D',
    '#E9B855',
    '#F1D676'
  ],
  neutralOrder: [[6], [3, 9], [1, 6, 11], [1, 4, 8, 11], [0, 3, 6, 9, 12], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  negativeOrder: [[1], [1, 9], [1, 6, 11], [1, 4, 8, 11], [12,9,6,3,0], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  positiveOrder: [[6], [4, 10], [1, 6, 11], [1, 4, 8, 11], [0, 3, 6, 9, 12], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  QUALITATIVE: 2,
  POSITIVE : 1,
  NEUTRAL : 0,
  NEGATIVE : -1
}



/**
 * Returns a color from a list
 * @param index number of color in a list
 * @param steps number of steps in a list
 * @param family 2 = qualitative; 1 = positive; 0 = neutral; otherwise negative
 * @returns
 */
function getBiganColor(family, steps, index ) {
  if (family == 2) {
	  return biganColors.qualitative[index];
  } else if (family == 1) {
    return biganColors.positive[biganColors.positiveOrder[steps - 1][index]]
  } else if (family == 0) {
    return biganColors.neutral[biganColors.neutralOrder[steps - 1][index]]
  } else {
    return biganColors.negative[biganColors.negativeOrder[steps - 1][index]]
  }
}


/**
 * return a list of colors from a given family
 * @param family
 * @param steps
 * @returns
 */
function getBiganColorList(family, steps) {
   var colors=[];  
   for (i=0;i<steps;++i){
	   colors.push(getBiganColor(family, steps, i));
   }
   return colors;
}



/**
 * Duerme durante un número especificado de milisegundos
 * @param ms
 * @returns
 */
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}




const biganSectors = ["11","12","21","31","32","41","42","51"];



/**
 * Procesa los nombres de sector. Convierte a capitalizado, excepto extensión de nombre
 * BARBASTRO -> Barbastro
 * ZARAGOZA III -> Zaragoza III
 * @param string
 * @returns
 */
function processSectorName (string) {
	var name_array = string.split(' ');
	name_array[0] = name_array[0].charAt(0).toUpperCase() + name_array[0].slice(1).toLowerCase(); 
	return name_array.join(' '); 
}
  
