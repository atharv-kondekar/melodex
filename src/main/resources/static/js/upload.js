document.addEventListener(
    "DOMContentLoaded",
    () => {

        /*
         * Only ADMIN can upload.
         */

        requireAdmin();

        setupNavigation();

        checkBackend();


        const input =
            document.getElementById(
                "thumbnail"
            );


        const preview =
            document.getElementById(
                "preview"
            );


        const fileName =
            document.getElementById(
                "fileName"
            );


        /*
         * File preview
         */

        input.addEventListener(
            "change",
            () => {

                const file =
                    input.files[0];


                if (!file) {

                    return;

                }


                fileName.textContent =
                    `${file.name} · ${formatBytes(
                        file.size
                    )}`;


                const url =
                    URL.createObjectURL(
                        file
                    );


                if (
                    file.type.startsWith(
                        "image/"
                    )
                ) {

                    preview.innerHTML =
                        `<img
                            src="${url}"
                            alt="Preview">`;

                }

                else if (
                    file.type.startsWith(
                        "video/"
                    )
                ) {

                    preview.innerHTML =
                        `<video
                            src="${url}"
                            controls>
                        </video>`;

                }

                else if (
                    file.type.startsWith(
                        "audio/"
                    )
                ) {

                    preview.innerHTML =
                        `<audio
                            src="${url}"
                            controls>
                        </audio>`;

                }

                else {

                    preview.textContent =
                        "Preview unavailable";

                }

            }
        );


        /*
         * Upload form
         */

        document
            .getElementById("uploadForm")
            .addEventListener(
                "submit",
                async event => {

                    event.preventDefault();


                    const title =
                        document
                            .getElementById(
                                "title"
                            )
                            .value
                            .trim();


                    const file =
                        input.files[0];


                    const message =
                        document.getElementById(
                            "message"
                        );


                    const button =
                        document.getElementById(
                            "submitBtn"
                        );


                    if (!title || !file) {

                        show(
                            message,
                            "Title and file are required.",
                            true
                        );

                        return;

                    }


                    const form =
                        new FormData();


                    form.append(
                        "title",
                        title
                    );


                    form.append(
                        "thumbnail",
                        file
                    );


                    button.disabled =
                        true;


                    button.textContent =
                        "Uploading...";


                    try {

                        const response =
                            await api(
                                "/albums",
                                {
                                    method: "POST",
                                    body: form
                                }
                            );


                        show(
                            message,
                            response?.message ||
                            "Album uploaded successfully."
                        );


                        event.target.reset();


                        fileName.textContent =
                            "No file selected";


                        preview.innerHTML =
                            '<span class="muted">Preview appears here</span>';

                    }
                    catch (error) {

                        show(
                            message,
                            error.message,
                            true
                        );

                    }
                    finally {

                        button.disabled =
                            false;


                        button.textContent =
                            "Upload album";

                    }

                }
            );

    }
);


/* =====================================
   MESSAGE
===================================== */

function show(
    element,
    text,
    error = false
) {

    element.textContent =
        text;


    element.className =
        `message ${
            error
                ? "error"
                : "success"
        }`;

}


/* =====================================
   FILE SIZE
===================================== */

function formatBytes(bytes) {

    if (!bytes) {

        return "0 B";

    }


    const units =
        [
            "B",
            "KB",
            "MB",
            "GB"
        ];


    const index =
        Math.floor(
            Math.log(bytes) /
            Math.log(1024)
        );


    return `${(
        bytes /
        Math.pow(
            1024,
            index
        )
    ).toFixed(
        index ? 1 : 0
    )} ${units[index]}`;

}