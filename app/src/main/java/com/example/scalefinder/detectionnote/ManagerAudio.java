/**Manager Audio permet de lire la sortie son.
 *
 * @Title : Manager Audio
 * @Author : Romain Salha
 * ManagerAudio();
 *
 * run();Lancer l'enregistrement
 * stop();Stop l'enregistrement
 * float getFrequenceForte();Retourne la fréquence la plus significative;
 */

package com.example.scalefinder.detectionnote;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;


public class ManagerAudio {

    private AudioRecord ar;
    private int frequence = 44100;
    private int bufferSizeRecorder=1024;
    private short[] buffer;
    private short[] bufferCP;

    private Thread thRecord;
    private boolean stop;


    private com.example.scalefinder.detectionnote.FrequencyScanner scanner;



    /**Creation de l'AudioRecord, du FrequencyScanner et
     * du Thread de l'enregistrement.
     */
    public ManagerAudio(){

        int minSize= AudioRecord.getMinBufferSize(frequence, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        ar = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
                frequence,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minSize);

        scanner  = new com.example.scalefinder.detectionnote.FrequencyScanner();

        thRecord = new Thread(
                new Runnable() {
                    public void run() {
                        record();
                    }
                });
    }

    /**Lance l'enregistrement de la sortie son.
     * NB: ne pas oublier de stop l'enregistrement...
     */
    public void run() {
        Log.i("AudioManager", "Lancement record thread");
        try {
            stop = false;
            thRecord.start();
        }catch (Throwable t) {
            Log.e("AudioManager", "erreur record");
        }
    }

    /**Stop l'enregistrement.
     */
    public void stop() {
        stop = true;
    }

    /**applique la Transformation de Fourier rapide à notre sample affin de trouver
     * la fréquence "principale".
     * Utilise la classe FrequencyScanner.
     * @return la fréquence la plus élevée de l'enregistrement
     */
    public double getFrequenceForte() {
        return scanner.extractFrequency(bufferCP,frequence);
    }

    /**
     *
     * @return le sample enregistré le plus récent
     */
    private short[] getSample() { return bufferCP;}
    private void record() {
        try {
            if (ar.getState() == AudioRecord.STATE_UNINITIALIZED) {
                Log.e("AudioManager", "Initialisation failed !");
                ar.release();
                ar = null;
                return;
            }

            buffer = new short[bufferSizeRecorder];
            ar.startRecording();

            while (!stop) {
                int bufferReadResult = ar.read(buffer, 0, bufferSizeRecorder);
                bufferCP = new short[bufferReadResult];
                System.arraycopy(buffer, 0, bufferCP, 0, bufferReadResult);
            }
            ar.stop();

        } catch (Throwable t) {
            Log.e("AudioRecord", "Erreur record");
        }

    }

}
