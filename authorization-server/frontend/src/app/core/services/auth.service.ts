import { Injectable } from '@angular/core';

/** This service is responsible for all kind of authentication actions. */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  /**
   * This property contains backend login url.
   *
   * @private
   */
  private readonly LOGIN_URL: string = '/login'

  /**
   * This function executes user login process.
   *
   * @param username provided user username
   * @param password provided user password
   */
  login(username: string, password: string) {
    let body = new URLSearchParams({
      "username": username,
      "password": password
    })

    fetch(this.LOGIN_URL, {
      method: 'POST',
      body: body,
      credentials: 'include'
    })
      .then(res => {
        if (res.redirected) {
          window.location.replace(res.url)
        }
      })
  }
}
