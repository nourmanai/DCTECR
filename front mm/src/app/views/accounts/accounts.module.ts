import { NO_ERRORS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AvatarModule, BadgeModule, ButtonGroupModule, ButtonModule, CardModule, FormModule, GridModule, NavModule, ProgressModule, TableModule, TabsModule } from '@coreui/angular';
import { ChartjsModule } from '@coreui/angular-chartjs';

import { AccountsComponent } from './accounts.component';
import { AccountsRoutingModule } from './accounts-routing.module';
import { DocsComponentsModule } from '@docs-components/docs-components.module';
import { HttpClientModule } from '@angular/common/http';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { LeafletMarkerClusterModule } from '@asymmetrik/ngx-leaflet-markercluster';
import { LeafletMarkerDirective } from './leaflet-marker.directive';
import { ReactiveFormsModule } from '@angular/forms';
import { IconModule, IconSetService  } from '@coreui/icons-angular';
import { WidgetsModule } from '../widgets/widgets.module';

import { PaginationModule } from 'ngx-bootstrap/pagination';
import { FormsModule } from '@angular/forms';

import { TruthyPipe } from './truthy.pipe';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

import { MatSelectModule } from '@angular/material/select';

import { CustomAvatarModule } from './../customavatar/customavatar.module';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AccountDetailsModule } from './../account-details/account-details.module';
import { WidgetsampleModule } from './../widgetsample/widgetsample.module';
import {
  ButtonDirective,
  ColComponent,
  DropdownComponent,
  DropdownItemDirective,
  DropdownMenuDirective,
  DropdownToggleDirective,
  RowComponent,
  TemplateIdDirective,
  WidgetStatAComponent
} from '@coreui/angular';


@NgModule({
  declarations: [AccountsComponent,LeafletMarkerDirective,TruthyPipe],
  imports: [
    
    MatCheckboxModule,
    MatSelectModule,
    CustomAvatarModule,
    NgbPaginationModule,
    FormsModule,
    PaginationModule.forRoot(),
    LeafletModule,
    LeafletMarkerClusterModule,
    CommonModule,
    AccountsRoutingModule,
    ChartjsModule,
    CardModule,
    GridModule,
    BadgeModule,
    HttpClientModule,
    DocsComponentsModule,
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
 
  ],
  providers: [IconSetService],
  schemas: [NO_ERRORS_SCHEMA],

})
export class AccountsModule {
}
