import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truthy'
})
export class TruthyPipe implements PipeTransform {
  transform<T>(value: T | null): T | undefined {
    return value as T;
  }
}