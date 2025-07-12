import { NO_ERRORS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AvatarModule, BadgeModule, ButtonGroupModule, ButtonModule, CardModule, FormModule, GridModule, NavModule, ProgressModule, TableModule, TabsModule } from '@coreui/angular';
import { ChartjsModule } from '@coreui/angular-chartjs';

import { SalesComponent } from './sales.component';
import { SalesRoutingModule } from './sales-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { LeafletMarkerClusterModule } from '@asymmetrik/ngx-leaflet-markercluster';
import { ReactiveFormsModule } from '@angular/forms';
import { IconModule, IconSetService  } from '@coreui/icons-angular';
import { WidgetsModule } from '../widgets/widgets.module';

import { PaginationModule } from 'ngx-bootstrap/pagination';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule, BsDatepickerConfig } from 'ngx-bootstrap/datepicker';


import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

import { MatSelectModule } from '@angular/material/select';

import { CustomAvatarModule } from './../customavatar/customavatar.module';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AccountDetailsModule } from './../account-details/account-details.module';
import { DropdownMenuModule } from './../dropdown-menu/dropdown-menu.module';

import { MatSliderModule } from '@angular/material/slider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { WidgetsampleModule } from './../widgetsample/widgetsample.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import {
  ButtonDirective,
  ColComponent,
  DropdownComponent,
  DropdownItemDirective,
  DropdownMenuDirective,
  DropdownToggleDirective,
  RowComponent,
  TemplateIdDirective,
  WidgetStatAComponent,
  SharedModule,
} from '@coreui/angular';
import { LeafletMarkerDirective } from './leaflet-marker.directive';


@NgModule({
    declarations: [SalesComponent, LeafletMarkerDirective],
    imports: [

      MatSliderModule,
      MatFormFieldModule,
      MatCheckboxModule,
      MatSelectModule,
      CustomAvatarModule,
      NgbPaginationModule,
      FormsModule,
      PaginationModule.forRoot(),
      BsDatepickerModule.forRoot(),
      LeafletModule,
      LeafletMarkerClusterModule,
     
      CommonModule,
      SalesRoutingModule,
      ChartjsModule,
      CardModule,
      GridModule,
      BadgeModule,
      HttpClientModule,
      ReactiveFormsModule,
     
      CardModule,
      NavModule,
      IconModule,
      TabsModule,
      CommonModule,
      GridModule,
      ProgressModule,
      ReactiveFormsModule,
      ButtonModule,
      FormModule,
      ButtonModule,
      ButtonGroupModule,
      ChartjsModule,
      AvatarModule,
      TableModule,
      WidgetsModule,
      WidgetsampleModule,
      SharedModule,
      
      DropdownComponent,
      ButtonDirective,
      DropdownToggleDirective,
      DropdownMenuDirective,
      DropdownItemDirective,
      RowComponent,
      TemplateIdDirective,
      WidgetStatAComponent,
      ColComponent,
      AccountDetailsModule,
      DropdownMenuModule,
      NgbModule,
  
   
    ],
    providers: [IconSetService,BsDatepickerConfig],
    schemas: [NO_ERRORS_SCHEMA],
    bootstrap: [SalesComponent]
  
  
  })
  export class SalesModule{}