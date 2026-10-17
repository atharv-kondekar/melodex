document
    .getElementById("loginForm")
    .addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const email =
                document.getElementById(
                    "email"
                ).value.trim();


            const password =
                document.getElementById(
                    "password"
                ).value;


            const message =
                document.getElementById(
                    "message"
                );


            const button =
                document.querySelector(
                    "#loginForm button[type='submit']"
                );


            try {

                button.disabled = true;

                button.textContent =
                    "Signing in...";


                const response =
                    await api(
                        "/users/login",
                        {

                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify({
                                    email: email,
                                    password: password
                                })

                        }
                    );


                console.log(
                    "Login response:",
                    response
                );


                const user =
                    unwrap(response);


                if (!user) {

                    throw new Error(
                        "Invalid login response from server."
                    );

                }


                /*
                 * Save the logged-in user.
                 */

                setSession(user);


                /*
                 * Redirect according
                 * to the user's role.
                 */

                if (
                    user.role ===
                    "ROLE_ADMIN"
                ) {

                    window.location.href =
                        "Admin.html";

                }
                else {

                    window.location.href =
                        "Albums.html";

                }

            }
            catch (error) {

                console.error(
                    "Login error:",
                    error
                );


                message.textContent =
                    error.message;


                message.className =
                    "message error";

            }
            finally {

                button.disabled = false;

                button.textContent =
                    "Sign in";

            }

        }
    );