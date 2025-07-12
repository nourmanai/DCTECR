import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {


transform(value: any[], filterTerm: string): any[] {
  let filteredArray = [...value]; // create a temporary array

  if (filterTerm) {
    filteredArray = filteredArray.filter(item => {
      return item.title.toLowerCase().includes(filterTerm.toLowerCase());
    });
  }

  return filteredArray;
}



}