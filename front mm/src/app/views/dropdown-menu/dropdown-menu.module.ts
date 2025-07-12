import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DropdownMenuComponent} from './dropdown-menu.component';
import { MatSelectModule } from '@angular/material/select';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';


@NgModule({
    declarations: [DropdownMenuComponent],
    imports: [CommonModule, MatSelectModule,NgbModule, FormsModule], 
    exports: [DropdownMenuComponent]
  })
  export class DropdownMenuModule {}