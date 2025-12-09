package org.odk.collect.android.utilities;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import timber.log.Timber;

public class ELAUtil {

    // Copy XML files from Downloads/myapp/forms to app's private directory
    public static void copyXmlFilesFromFormsDirectory(Context context, Uri fileUri, String projectId) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            InputStream inputStream = contentResolver.openInputStream(fileUri);
            if (inputStream != null) {
                File appFormsDir = context.getExternalFilesDir(null); // Private app data directory
                String fileName = getRealFileName(contentResolver, fileUri);
                File outputFile = new File(appFormsDir, "/projects/" + projectId + "/forms/" + fileName);

                // Write input stream to the app's private directory
                OutputStream outputStream = new FileOutputStream(outputFile);
                copyStream(inputStream, outputStream);

                inputStream.close();
                outputStream.close();

                Timber.tag("ELAUtil").d("Copied " + fileName + " to private directory.");
            }
        } catch (IOException e) {
            Timber.tag("ELAUtil").e(e, "Error copying file to forms directory");
        }
    }

    public static String getRealFileName(ContentResolver resolver, Uri uri) {
        String fileName = null;
        Cursor cursor = null;

        try {
            // Query the content resolver for the DISPLAY_NAME column
            cursor = resolver.query(uri, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // Get the index of the DISPLAY_NAME column
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex);
                }
            }
        } catch (Exception e) {
            Timber.tag("ELAUtil").e(e, "Error getting real filename for file Uri");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // If no file name is found, try using the last path segment (as a fallback)
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }

        return fileName;
    }

    // Utility method to copy an InputStream to an OutputStream
    private static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
    }
}
