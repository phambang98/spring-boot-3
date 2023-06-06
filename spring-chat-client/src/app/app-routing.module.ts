import {NgModule} from '@angular/core';

import {Routes, RouterModule} from '@angular/router';
import {TestComponent} from "./home/test/test.component";

const routes: Routes = [
  {path: '', redirectTo: 'loading', pathMatch: 'full'},
  {path: 'test', component: TestComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
