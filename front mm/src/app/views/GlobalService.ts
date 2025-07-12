import {  Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import * as iso3166 from 'iso-3166-1-alpha-2';




@Injectable({
  providedIn: 'root'
})
export class GlobalService {



    constructor(private http: HttpClient) {}

  

    public getCategoryTree(allcategories: any[]): any[] {
      const tree: any[] = [];
      const rootCategories = allcategories.filter(category => category.parent === null);
    
      for (const category of rootCategories) {
        category.children = this.getCategoryTreeRecursive(category, allcategories);
        tree.push(category);
      }
    
      console.log("getCategoryTree tree ", tree);
      return tree;
    }
    
   

    // getCategoryTreeRecursive(parent: any, allcategories: any[]): any[] {
    //   const tree: any[] = [];
    //   for (const category of allcategories) {
    //     if (category.parent && category.parent.id === parent.id) {
    //       const children = this.getCategoryTreeRecursive(category, allcategories);
    //       category.children = children;
    //       tree.push(category);
    //       allcategories = tree.flatMap(category => category.children);

    //       for (const category of allcategories) {
    //         category.children = this.getCategoryTreeRecursive(category, allcategories);

           
    //       }
    //     }
    //   }
    //   return tree;
    // }
    getCategoryTreeRecursive(parent: any, allcategories: any[], currentLevel: any[] = []): any[] {
      const tree: any[] = [];
      for (const category of allcategories) {
        if (category.parent && category.parent.id === parent.id) {
          category.children = this.getCategoryTreeRecursive(category, allcategories, currentLevel.concat([category]));
          tree.push(category);
        }
      }
      return tree;
    }

     displayCategoryTree(categories: any[]): string {
      let html = '<ul>';
      for (const category of categories) {
        html += `<li>${category.fields.name}`;
        if (category.children) {
          html += this.displayCategoryTree(category.children);
        }
        html += '</li>';
      }
      html += '</ul>';
      return html;
    }
    
   
    getRound(nbr: number): number {
      return Math.round(nbr);
    }

    getItemByObject(key: string, value: any, data: any[]): any {
      return data.find(item => item[key] === value);
    }
    
     getInfo(property: string, data: any): any {
        return data ? data.fields[property] : undefined;
      }


      // /retrieve-campaign-by-field/{fieldName}/{value}

      getObjectById(Class: string, fieldName: string , value: string) 
      {
        const headers = new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Basic',
        credentials: 'include',
      });
      
    
      return this.http.get<any>(`http://localhost:8082/ExamenBlanc/${Class}/retrieve-${Class}-by-field/${fieldName}/${value}`, { headers });
    }
    retrieveall(className:any, listName : any ):any{
      const headers = new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Basic',
        credentials: 'include',
      });
    
      return this.http.get<any[]>(`http://localhost:8082/ExamenBlanc/${className}/retrieve-all-${listName}`, { headers });
    }

      // getObjectById(data: any, key: any): any {
      //   return data ? data.id == key : undefined;
      // }
      CountItems( data: any ,key:any, maxvalue:any , minvalue:any): number{

        let count:number=0;
        if(maxvalue !==null && minvalue !== null)
        {
          data= data.filter((item: any) => {  
            const found = this.getInfo(key, item)>=minvalue && this.getInfo(key, item)<=maxvalue;   
            if (found) { count++; }        
          });
    
          return count;
        } 
        else {
          data= data.filter((item: any) => { 
            const found = this.getInfo(key, item)==maxvalue;    
            if (found) { count++; }         
          });     
               return count;
        }
      }

      formatNumber(value: number): string {
        if (Math.abs(value) >= 1000000000) {
            return (value / 1000000000).toFixed(1) + 'B';
        } else if (Math.abs(value) >= 1000000) { 
            return (value / 1000000).toFixed(1) + 'M';
        } else if (Math.abs(value) >= 1000) {
            return (value / 1000).toFixed(1) + 'K';
        } else {
            return value.toFixed(0); 
        }
    }
    
      
  getCategoryOptions(data:any) : any [] {
    const categories = data.map((item: any) => this.getInfo('category', item)); 
      const uniquecategorySet = new Set(categories);
      const categoryOptions = Array.from(uniquecategorySet);
      return categoryOptions; 
  }

  getEntityByKey( data: any, key:any, value:any) {
    let foundItem: any ;
    data= data.filter((item: any) => {  
       const found = this.getInfo(key, item)== value;  
      if (found) { foundItem = item; }        
    });

    return foundItem;

  }

   getUniqueValues(property: string, data: any[]): any[] {
    const values: any[] = [];
  
    // Iterate over each object in the data array
    data.forEach(item => {
      if(property.includes('.')){
        const [first, second] = property.split('.');
        const value = item[first][0].fields[second];

        if (values.indexOf(value) === -1) {
            values.push(value);
        }
      }
    else{  
      const value = item.fields[property];
      if (values.indexOf(value) === -1) {
        values.push(value);
      }
    }
    });
  
    return values;
  }
  
  // getUniquePriceRangeValues(data: any[]): any[] {
  //   const values: any[] = [];
  
  //   data.forEach(item => {
  //     const priceRange = item.productSKU.fields.typical_price_range;
  
  //     // Check if priceRange is an array
  //     if (Array.isArray(priceRange)) {
  //       priceRange.forEach((price: any) => {
  //         if (!values.includes(price)) {
  //           values.push(price);
  //         }
  //       });
  //     } else {
  //       if (!values.includes(priceRange)) {
  //         values.push(priceRange);
  //       }
  //     }
  //   });
  
  //   return values;
  // }
  
  getUniquePriceRangeValues(data: any[]): any[] {
    const values: any[] = [];
  
    data.forEach(item => {
      const priceRange = item.productSKU.fields.typical_price_range;
  
      // Check if priceRange is an array
      if (Array.isArray(priceRange)) {
        // Stringify the array to compare it as a single value
        const priceRangeStr = JSON.stringify(priceRange);
        if (!values.some(v => JSON.stringify(v) === priceRangeStr)) {
          values.push(priceRange);
        }
      } else {
        // If it's not an array, just add it if it's unique
        if (!values.includes(priceRange)) {
          values.push(priceRange);
        }
      }
    });
  
    return values;
  }
  
  getUniqueOptions(data: any[], objectKey: string, key: string): any[] {

    if (objectKey !== '')
  {  const values = data.map(order => order[objectKey]?.fields[key]);

    const filteredValues = values.filter(value => value !== undefined && value !== null);

    const uniqueOptions = Array.from(new Set(filteredValues));

    return uniqueOptions;}
    else {
      const values = data.map(order => order?.fields[key]);

      const filteredValues = values.filter(value => value !== undefined && value !== null);
  
      const uniqueOptions = Array.from(new Set(filteredValues));
  
      return uniqueOptions;

    }
  }
  
  

  formatDate(dateString: string, returnType: 'date' | 'time'): string {
    const date = new Date(dateString);
    if (returnType === 'date') {
        return date.toLocaleDateString();
    } else if (returnType === 'time') {
        return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    } else {
        throw new Error('Invalid return type. Use "date" or "time".');
    }
}

SliceTitle(title:any) : any{
  const commaIndex = title.indexOf(',');
  const pipeIndex = title.indexOf('|');
  let endIndex = -1;
  if (commaIndex !== -1 && pipeIndex !== -1) {
    endIndex = Math.min(commaIndex, pipeIndex);
  } else if (commaIndex !== -1) {
    endIndex = commaIndex;
  } else if (pipeIndex !== -1) {
    endIndex = pipeIndex;
  }
    return endIndex !== -1 ? title.slice(0, endIndex): title;
 }

 extractDiscountPercentage(discountPercentage: string): number {
  const numericValue = discountPercentage.replace(/[^0-9]/g, '');
  return parseInt(numericValue, 10);
}
getProgressBarColor(discount: number): string {
  if (discount >= 50) {
    return 'high-discount';
  } else if (discount >= 20) {
    return 'medium-discount';
  } else {
    return 'low-discount';
  }
}

getStockStatus(units: number): string {
  if (units <= 100) {
    return 'ending';
  } else if (units <= 500) {
    return 'about-to-end';
  } else {
    return 'available';
  }
}
formatPriceRange(priceRange: any): string {
  if (Array.isArray(priceRange) && priceRange.length === 2) {
    return `${priceRange[0]} - ${priceRange[1]}`;
  }
  return priceRange;
}

// IdianRupeeToUsd( yen : number): number{ return yen*83.36;}

convertMoney(toConvert: number , conversionRate:number): number {
  return toConvert * conversionRate;
}

getRandomColor(): string {
  const colors = [
  'red', 'orange', 'yellow', 'green', 'blue', 'purple', 'pink', 'brown', 'teal', 'cyan',
  'maroon', 'navy', 'olive', 'lime', 'indigo', 'magenta', 'peach', 'turquoise', 'lavender'
 ];
  const randomIndex = Math.floor(Math.random() * colors.length);
  return colors[randomIndex];
 }

 getArrayofRandomColors( nbr : number) : String[]{

  const colors = new Set<string>();

  while (colors.size < nbr) {
    const color = this.getRandomColor();
    if (!colors.has(color)) {
      colors.add(color);
    }
  }

  return Array.from(colors);

 }



getRandomColorforChart(): string {
  const letters = '0123456789ABCDEF';
  let color = '#';
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}

getArrayofRandomColorsforChart(nbr: number): string[] {
  const colors = new Set<string>();

  while (colors.size < nbr) {
    const color = this.getRandomColorforChart();
    if (!colors.has(color)) {
      const rgbaColor = `rgba(${parseInt(color.slice(1, 3), 16)}, ${parseInt(color.slice(3, 5), 16)}, ${parseInt(color.slice(5, 7), 16)}, 0.2)`;
      colors.add(rgbaColor);
    }
  }

  return Array.from(colors);
}

getConsistentColorMap(values: number[]): { [key: number]: string } {
  const uniqueValues = Array.from(new Set(values)).sort((a, b) => a - b);
  const colorMap: { [key: number]: string } = {};
  const colors = this.getArrayofRandomColorsforChart(uniqueValues.length);

  uniqueValues.forEach((value, index) => {
    colorMap[value] = colors[index];
  });

  return colorMap;
}
removeSpecialCharacters(str: string): string {
  const accentedChars = /[À-ÿ]/g;
  const nonAccentedChars = str.replace(accentedChars, function(char) {
    const nonAccentedChar = char.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
    return nonAccentedChar;
  });
  return nonAccentedChars.replace(/[^a-zA-Z0-9\s]/g, '');
}
countryNameMappings: { [key: string]: string } = {
  'brasil': 'Brazil',
  'españa': 'Spain'
};
standardizeCountryName(country: string): string {
  const standardCountryName = (this.countryNameMappings[country.toLowerCase()]) || country;
  return standardCountryName;
}
loadCountries(location: any): string {
  const standardCountryName = this.standardizeCountryName(location);
  const countryCode = iso3166.getCode(this.removeSpecialCharacters(standardCountryName));
  if (countryCode && countryCode.length >= 2) {
    return countryCode.toUpperCase();
  } else {
    return ''; 
  }
}
getFlagIcon(countryCode: string): string {
  return `cif-${countryCode.toLowerCase()}`;
}

Floornumber(number:any):any{
  return Math.floor(number);
}

}