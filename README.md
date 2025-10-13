# !! THE APP IS NEWLY FORKED (AS OF 13 OCT 2025). WORK IS IN PROGRESS TO UPDATE THE APP LOGO, DEPLOYMENT CONFIGURATIONS, LICENSE NOTICES ETC !!

# ELA Collect

![Platform](https://img.shields.io/badge/platform-Android-blue.svg)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

ELA Collect is powered by the excellent ODK Collect app. ELA is E Learning Assessment. The ELA app has customizations on top of the ODK collect app to support fully offline form download and submission upload where a desktop workstation acts as a server and runs the ELA server component. The name 'ELA' reflects the initial use case - teachers use the app to assess student performance.

Learn more about ODK and its history [here](https://getodk.org/).

## Significant changes made to the ODK Collect App
* Modified the app to 'Download' forms from the android device. The app looks for XML files in the directory ```/Download/ela/forms```. The app does no validation of the XML. The XMLs will be added to the list of available forms in the app if they are valid ODK Collect compatible xform XML files. ODK Collect's ability to download forms from a remote server is retained.
* Modified the app so the user can export form submissions as a ZIP file using the Android OS's Intent capability. When a user chooses to send all submissions to the server, the app collects all submission XML files and media files if any into a ZIP file, and opens the 'Share with' popup (technically, the 'Sharesheet') instead of submitting the forms to a server. The user can choose an app from the sharesheet to export the ZIP file to a destination, such as a workstation connected over Bluetooth. ODK Collect's ability to submit forms to a remote server is disabled.
* Miscellaneous updates to make the app ready for deployment on an android device.
