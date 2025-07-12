
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { AccountDetailsComponent } from './account-details.component';
import { CommonModule } from '@angular/common';
import { AvatarModule, BadgeModule, ButtonGroupModule, ButtonModule, CardModule, FormModule, GridModule, NavModule, ProgressModule, TableModule, TabsModule } from '@coreui/angular';
import { CustomAvatarModule } from './../customavatar/customavatar.module';
import { HttpClientModule } from '@angular/common/http';
import { WidgetsModule } from '../widgets/widgets.module';
import { IconModule, IconSetService  } from '@coreui/icons-angular';



@NgModule({
    declarations: [AccountDetailsComponent],
    imports: [
        IconModule,
        CommonModule,
        CustomAvatarModule,
        HttpClientModule,
        AvatarModule, BadgeModule, ButtonGroupModule, ButtonModule, CardModule, FormModule, GridModule, NavModule, ProgressModule, TableModule, TabsModule,
        WidgetsModule,
    ],

    providers: [IconSetService],
    schemas: [NO_ERRORS_SCHEMA],
    exports: [AccountDetailsComponent]
  })
  export class AccountDetailsModule {
  }