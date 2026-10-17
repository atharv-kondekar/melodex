const API_BASE = "http://localhost:8081";


/* =====================================
   COMMON API FUNCTION
===================================== */

async function api(path, options = {}) {

    const response = await fetch(
        API_BASE + path,
        options
    );

    const text = await response.text();

    let data;

    try {

        data = text
            ? JSON.parse(text)
            : null;

    } catch {

        data = text;

    }


    if (!response.ok) {

        throw new Error(
            data?.message ||
            data?.error ||
            `Request failed (${response.status})`
        );

    }


    return data;
}


/* =====================================
   SESSION MANAGEMENT
===================================== */

function setSession(user) {

    sessionStorage.setItem(
        "user",
        JSON.stringify(user)
    );

}


function getSession() {

    const user =
        sessionStorage.getItem("user");


    if (!user) {

        return null;

    }


    try {

        return JSON.parse(user);

    } catch {

        sessionStorage.removeItem("user");

        return null;

    }

}


function clearSession() {

    sessionStorage.removeItem("user");

}


function isLoggedIn() {

    return getSession() !== null;

}


function isAdmin() {

    const user = getSession();

    return user &&
           user.role === "ROLE_ADMIN";

}


/* =====================================
   PAGE PROTECTION
===================================== */

function requireLogin() {

    if (!isLoggedIn()) {

        window.location.href =
            "Login.html";

    }

}


function requireAdmin() {

    const user = getSession();


    if (!user) {

        window.location.href =
            "Login.html";

        return;

    }


    if (user.role !== "ROLE_ADMIN") {

        window.location.href =
            "Albums.html";

    }

}


/* =====================================
   NAVIGATION
===================================== */

function setupNavigation() {

    const user = getSession();


    if (!user) {

        window.location.href =
            "Login.html";

        return;

    }


    /* Username */

    const userName =
        document.getElementById("userName");

    if (userName) {

        userName.textContent =
            user.name || user.email;

    }


    /* Role */

    const userRole =
        document.getElementById("userRole");

    if (userRole) {

        userRole.textContent =
            user.role || "USER";

    }


    /*
     * Customer:
     * Hide admin-only navigation.
     */

    if (user.role !== "ROLE_ADMIN") {

        const adminLink =
            document.getElementById("adminLink");

        const usersLink =
            document.getElementById("usersLink");

        const uploadLink =
            document.getElementById("uploadLink");

        const addAlbumButton =
            document.getElementById(
                "addAlbumButton"
            );


        if (adminLink) {

            adminLink.style.display =
                "none";

        }


        if (usersLink) {

            usersLink.style.display =
                "none";

        }


        if (uploadLink) {

            uploadLink.style.display =
                "none";

        }


        if (addAlbumButton) {

            addAlbumButton.style.display =
                "none";

        }

    }

}


/* =====================================
   LOGOUT
===================================== */

function logout() {

    clearSession();

    window.location.href =
        "Login.html";

}


/* =====================================
   BACKEND STATUS
===================================== */

async function checkBackend() {

    const status =
        document.getElementById(
            "backendStatus"
        );


    if (!status) {

        return;

    }


    try {

        /*
         * We use /albums simply to
         * check whether the backend
         * is responding.
         */

        const response =
            await fetch(
                API_BASE + "/albums"
            );


        if (response.ok) {

            status.innerHTML =
                '<span class="dot online"></span> Backend online';

        }
        else {

            status.innerHTML =
                '<span class="dot offline"></span> Backend error';

        }

    }
    catch {

        status.innerHTML =
            '<span class="dot offline"></span> Backend offline';

    }

}


/* =====================================
   RESPONSE HELPERS
===================================== */

function unwrap(response) {

    return response?.data ?? response;

}


function mediaUrl(fileName) {

    if (!fileName) {
        return "";
    }

    return `${API_BASE}/albums/media/${encodeURIComponent(fileName)}`
}


function escapeHtml(value) {

    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");

}