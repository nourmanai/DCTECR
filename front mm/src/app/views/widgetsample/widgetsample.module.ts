
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { WidgetsampleComponent } from './widgetsample.component';
import { CommonModule } from '@angular/common';
import { AvatarModule, BadgeModule, ButtonGroupModule, ButtonModule, CardModule, FormModule, GridModule, NavModule, ProgressModule, TableModule, TabsModule, TextColorDirective, WidgetStatEComponent } from '@coreui/angular';
import { CustomAvatarModule } from './../customavatar/customavatar.module';
import { HttpClientModule } from '@angular/common/http';
import { WidgetsModule } from '../widgets/widgets.module';
import { IconDirective, IconModule, IconSetService  } from '@coreui/icons-angular';
import { MatSliderModule } from '@angular/material/slider';
import { MatCheckboxModule } from '@angular/material/checkbox';

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
import { RouterLink } from '@angular/router';
import { ChartjsComponent } from '@coreui/angular-chartjs';
import { MatMenuModule } from '@angular/material/menu';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormControlDirective, FormLabelDirective } from '@coreui/angular';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
    declarations: [WidgetsampleComponent],
    imports: [
        IconModule,
        CommonModule,
        CustomAvatarModule,
        HttpClientModule,
        AvatarModule, BadgeModule, ButtonGroupModule, ButtonModule, CardModule, FormModule, GridModule, NavModule, ProgressModule, TableModule, TabsModule,
        WidgetsModule,
        RowComponent,
        ColComponent,
        TextColorDirective,
        WidgetStatEComponent,
        RowComponent,
        ColComponent,
        WidgetStatAComponent,
        TemplateIdDirective,
        IconDirective,
        DropdownComponent,
        ButtonDirective,
        DropdownToggleDirective,
        DropdownMenuDirective,
        DropdownItemDirective,
        RouterLink,
        MatSliderModule,
        MatMenuModule,
        ChartjsComponent,
        MatInputModule,
       FormsModule,
       ReactiveFormsModule,
       MatCheckboxModule,
       FormControlDirective,
        FormLabelDirective,
        MatSelectModule
    ],

    providers: [IconSetService],
    schemas: [NO_ERRORS_SCHEMA],
    exports: [WidgetsampleComponent]
  })
  export class WidgetsampleModule {
  }