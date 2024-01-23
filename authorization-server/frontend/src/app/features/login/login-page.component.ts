import { Component } from "@angular/core";
import { LoginFormComponent } from "./components/login-form/login-form.component";

/** This component represents login page. */
@Component({
  selector: 'login-page',
  templateUrl: './login-page.component.html',
  standalone: true,
  imports: [ LoginFormComponent ]
})
export class LoginPageComponent {
}
