import { Component } from "@angular/core";
import { AuthService } from "../../../../core/services/auth.service";
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from "@angular/forms";

/** This component represents user login form. */
@Component({
  selector: 'login-form',
  templateUrl: './login-form.component.html',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  standalone: true
})
export class LoginFormComponent {

  /** This property represents login form form-group. */
  formGroup: FormGroup;

  /**
   * Constructs login form component.
   *
   * @param authService injected authentication service instance
   */
  constructor(
    private readonly authService: AuthService
  ) {
    this.formGroup = new FormGroup({
      username: new FormControl<string | null>(null),
      password: new FormControl<string | null>(null)
    });
  }

  /** This function encapsulates actions, that activate on form submit. */
  performLogin(): void {
    let username = this.formGroup.controls['username'].value;
    let password = this.formGroup.controls['password'].value;
    this.authService.login(username, password);
  }
}
