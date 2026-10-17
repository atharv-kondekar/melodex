document.addEventListener(
    "DOMContentLoaded",
    () => {

        /*
         * Only ADMIN can access users.
         */

        requireAdmin();

        setupNavigation();

        checkBackend();

        loadUsers();


        const newUserButton =
            document.getElementById(
                "newUser"
            );


        if (newUserButton) {

            newUserButton.addEventListener(
                "click",
                createUser
            );

        }

    }
);


/* =====================================
   LOAD USERS
===================================== */

async function loadUsers() {

    const body =
        document.getElementById(
            "usersBody"
        );


    try {

        const users =
            unwrap(
                await api("/users")
            ) || [];


        body.innerHTML =
            users.length

                ? users
                    .map(
                        user => `

                            <tr>

                                <td>
                                    ${user.id ?? "—"}
                                </td>

                                <td>
                                    ${escapeHtml(
                                        user.name
                                    )}
                                </td>

                                <td>
                                    ${escapeHtml(
                                        user.email
                                    )}
                                </td>

                                <td>
                                    ${escapeHtml(
                                        user.address
                                    )}
                                </td>

                                <td>
                                    ${escapeHtml(
                                        user.role ||
                                        "—"
                                    )}
                                </td>

                                <td>

                                    <button
                                        class="btn"
                                        onclick="updateEmail(
                                            ${user.id}
                                        )">

                                        Edit email

                                    </button>


                                    <button
                                        class="btn danger"
                                        onclick="deleteUser(
                                            ${user.id}
                                        )">

                                        Delete

                                    </button>

                                </td>

                            </tr>

                        `
                    )
                    .join("")

                : `
                    <tr>

                        <td colspan="6">
                            No users found.
                        </td>

                    </tr>
                `;

    }
    catch (error) {

        body.innerHTML = `

            <tr>

                <td colspan="6">

                    ${escapeHtml(
                        error.message
                    )}

                </td>

            </tr>

        `;

    }

}


/* =====================================
   CREATE USER
===================================== */

async function createUser() {

    const name =
        prompt("Name:");


    const email =
        prompt("Email:");


    const password =
        prompt("Password:");


    if (
        !name ||
        !email ||
        !password
    ) {

        return;

    }


    try {

        await api(
            "/users",
            {

                method: "POST",

                headers: {
                    "Content-Type":
                        "application/json"
                },

                body:
                    JSON.stringify({

                        name: name,

                        email: email,

                        password: password,

                        address: "",

                        birth: null

                    })

            }
        );


        loadUsers();

    }
    catch (error) {

        alert(
            error.message
        );

    }

}


/* =====================================
   UPDATE EMAIL
===================================== */

async function updateEmail(id) {

    const email =
        prompt(
            "New email:"
        );


    if (!email) {

        return;

    }


    try {

        await api(
            `/users/${id}`,
            {

                method: "PATCH",

                headers: {
                    "Content-Type":
                        "application/json"
                },

                body:
                    JSON.stringify({
                        email: email
                    })

            }
        );


        loadUsers();

    }
    catch (error) {

        alert(
            error.message
        );

    }

}


/* =====================================
   DELETE USER
===================================== */

async function deleteUser(id) {

    if (
        !confirm(
            `Delete user #${id}?`
        )
    ) {

        return;

    }


    try {

        await api(
            `/users/${id}`,
            {
                method: "DELETE"
            }
        );


        loadUsers();

    }
    catch (error) {

        alert(
            error.message
        );

    }

}