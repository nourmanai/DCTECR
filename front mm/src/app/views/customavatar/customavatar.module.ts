import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomAvatarComponent } from './customavatar.component'; 
import { AvatarModule } from '@coreui/angular'; 

@NgModule({
  declarations: [CustomAvatarComponent],
  imports: [CommonModule, AvatarModule], 
  exports: [CustomAvatarComponent]
})
export class CustomAvatarModule {}
