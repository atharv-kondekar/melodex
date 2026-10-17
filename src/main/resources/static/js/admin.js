document.addEventListener(
    "DOMContentLoaded",
    async () => {

        /*
         * Only ADMIN can enter
         * this page.
         */

        requireAdmin();

        setupNavigation();

        checkBackend();


        const user =
            getSession();


        const welcomeName =
            document.getElementById(
                "welcomeName"
            );


        if (welcomeName && user) {

            welcomeName.textContent =
                user.name;

        }


        /* =================================
           TOTAL ALBUMS
        ================================= */

        try {

            const albums =
                unwrap(
                    await api("/albums")
                ) || [];


            const albumCount =
                document.getElementById(
                    "albumCount"
                );


            if (albumCount) {

                albumCount.textContent =
                    albums.length;

            }

        }
        catch {

            document.getElementById(
                "albumCount"
            ).textContent = "—";

        }


        /* =================================
           TOTAL USERS
        ================================= */

        try {

            const users =
                unwrap(
                    await api("/users")
                ) || [];


            const userCount =
                document.getElementById(
                    "userCount"
                );


            if (userCount) {

                userCount.textContent =
                    users.length;

            }

        }
        catch {

            document.getElementById(
                "userCount"
            ).textContent = "—";

        }

    }
);