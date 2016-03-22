package com.github.browep.privatephotovault;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.util.Log;

import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;

import java.io.File;

/**
 * camera host for taking breakin photos
 */
public class BreakInCameraHost extends SimpleCameraHost {

    public static final String TAG = BreakInCameraHost.class.getCanonicalName();

    private File outFile;

    public BreakInCameraHost(Context _ctxt, File outFile) {
        super(_ctxt);
        this.outFile = outFile;
    }

    public static void adjustOrientationParameters(Camera.Parameters parameters, boolean usingFrontFacing, int takenRotation) {
        if (!usingFrontFacing) {
            int rotationToSet;
            switch (takenRotation) {
                case 0:
                    rotationToSet = 90;
                    break;
                case 1:
                    rotationToSet = 0;
                    break;
                case 3:
                    rotationToSet = 180;
                    break;
                default:
                    rotationToSet = -1;
            }

            if (rotationToSet >= 0) {
                parameters.setRotation(rotationToSet);
            }
        } else {
            int rotationToSet;
            switch (takenRotation) {
                case 0:
                    rotationToSet = 270;
                    break;
                case 1:
                    rotationToSet = 0;
                    break;
                case 3:
                    rotationToSet = 180;
                    break;
                default:
                    rotationToSet = -1;
            }

            if (rotationToSet >= 0) {
                parameters.setRotation(rotationToSet);
            }
        }

    }

    @Override
    protected File getPhotoDirectory() {
        return outFile.getParentFile();
    }

    @Override
    protected File getPhotoPath() {
        return outFile;
    }

    @Override
    protected String getPhotoFilename() {
        return outFile.getName();
    }

    @Override
    public void saveImage(PictureTransaction xact, byte[] image) {
        super.saveImage(xact, image);
        Log.d(TAG, "image saved: " + getPhotoPath().getAbsolutePath());
    }

    @Override
    protected boolean useFrontFacingCamera() {
        return true;
    }

    @Override
    protected boolean scanSavedImage() {
        return false;
    }

    @Override
    public Camera.Parameters adjustPictureParameters(PictureTransaction xact, Camera.Parameters parameters) {

        adjustOrientationParameters(parameters, true, 0);

        return super.adjustPictureParameters(xact, parameters);
    }

    @Override
    public boolean useSingleShotMode() {
        return true;
    }
}
