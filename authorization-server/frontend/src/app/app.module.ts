import { NgModule } from "@angular/core";
import { AppComponent } from "./app.component";
import { BrowserModule } from "@angular/platform-browser";
import { RouterModule, RouterOutlet } from "@angular/router";
import { AppRoutingModule } from "./app-routing.module";

/** This module is a typical application base. Most of the components are standalone and do not require modules. */
@NgModule({
  declarations: [ AppComponent ],
  imports: [ BrowserModule, RouterOutlet, AppRoutingModule ],
  exports: [ RouterModule ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
}
