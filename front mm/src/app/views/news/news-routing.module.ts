import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { NewsComponent} from './news.component';

const routes: Routes = [
  {
    path: '',
    component: NewsComponent,
    data: {
      title: 'News',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NewsRoutingModule{}