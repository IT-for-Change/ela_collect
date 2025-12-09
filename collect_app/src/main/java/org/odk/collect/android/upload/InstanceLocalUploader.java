package org.odk.collect.android.upload;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.odk.collect.android.utilities.WebCredentialsUtils;
import org.odk.collect.forms.instances.Instance;
import org.odk.collect.forms.instances.InstancesRepository;
import org.odk.collect.openrosa.http.OpenRosaHttpInterface;
import org.odk.collect.shared.settings.Settings;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import timber.log.Timber;

public class InstanceLocalUploader extends InstanceUploader {

    private static final String URL_PATH_SEP = "/";
    private final Settings generalSettings;

    private ZipOutputStream _zos = null;

    private File _zipFile = null;

    public InstanceLocalUploader(Settings generalSettings, InstancesRepository instancesRepository) {
        super(instancesRepository);
        this.generalSettings = generalSettings;
    }

    public void addToZip(Instance instance) throws Exception {

        File instanceFile = new File(instance.getInstanceFilePath());

        if (_zos == null) {
            //jump 2 levels - from the instance submission xml to the instance submission directory
            //to the 'instances' directory.
            _zipFile = new File(instanceFile.getParentFile().getParentFile(), generateZipFileName());
            FileOutputStream fos = new FileOutputStream(_zipFile);
            _zos = new ZipOutputStream(fos);
        }

        File[] allFiles = instanceFile.getParentFile().listFiles();

        for (File f : allFiles) {
            ZipEntry zipEntry = new ZipEntry(f.getName());
            _zos.putNextEntry(zipEntry);
            try (FileInputStream fis = new FileInputStream(f)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    _zos.write(buffer, 0, bytesRead);
                }
            }
            _zos.closeEntry();
        }

        return;
    }

    private String generateZipFileName() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = dateFormat.format(currentDate);
        return "SUBMISSIONS_" + formattedDate + ".zip";
    }

    public File getZipFile(){
        return _zipFile;
    }

    @Nullable
    @Override
    public String uploadOneSubmission(Instance instance, String destinationUrl) throws FormUploadException {
        return "";
    }

    @NonNull
    @Override
    public String getUrlToSubmitTo(Instance currentInstance, String deviceId, String overrideURL, String urlFromSettings) {
        return "";
    }
}
