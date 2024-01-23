import { NgModule } from "@angular/core";
import { PreloadAllModules, RouterModule, Routes } from "@angular/router";
import { LoginPageComponent } from "./features/login/login-page.component";

const routes: Routes = [
  {
    path: 'login',
    pathMatch: "full",
    component: LoginPageComponent
  }
];

/** This module encapsulates application routes information. */
@NgModule({
  imports: [ RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
