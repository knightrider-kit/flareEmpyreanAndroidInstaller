package mkk.flare.empyrian;

import android.app.*;

import android.content.*;

import android.os.*;

import android.widget.*;

import java.io.*;

import java.util.zip.*;

public class MainActivity extends Activity 

{

    @Override

    protected void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

		String sdCard = Environment.getExternalStorageDirectory().toString();		String intStr = getFilesDir().toString();

		File f = new File (sdCard + "/Flare");

		

//		Toast.makeText(this, intStr, Toast.LENGTH_LONG).show();

		

		if(!f.exists())

			if(!f.mkdir())

				Toast.makeText(this, f+" can't be created", Toast.LENGTH_LONG).show();

				

				else

		try

			{

			sdCopy();

			fileCopy();

			unzip(new File (sdCard+"/Flare/mod.zip"), new File (sdCard+"/Flare"));

			unzip(new File (sdCard+"/Flare/emp.zip"), new File (sdCard+"/Flare/mod"));

			java.lang.Process mkkb = Runtime.getRuntime().exec("su -c "+intStr+"/go");

			}

				catch (IOException e)

				{}

		else

		try

			{

			sdCopy();

				fileCopy();

				unzip(new File (sdCard+"/Flare/mod.zip"), new File (sdCard+"/Flare"));

				unzip(new File (sdCard+"/Flare/emp.zip"), new File (sdCard+"/Flare/mods"));

				java.lang.Process mkkb = Runtime.getRuntime().exec("su -c "+intStr+"/go");

			}

				catch (IOException e)

				{}

			

	}

		public void sdCopy()

		throws FileNotFoundException, IOException

		{

		String sdCard = Environment.getExternalStorageDirectory().toString();

		File modFile = new File (sdCard + "/Flare/mod.zip");

		File apkFile = new File (sdCard + "/Flare/flare.apk");

		File empFile = new File (sdCard + "/Flare/emp.zip");

		

			

			InputStream fis = MainActivity.this.getResources().openRawResource(R.raw.mod);

			OutputStream fos = new FileOutputStream(modFile);

				byte[] buf = new byte[1024];

				int len;

					

				while ((len = fis.read(buf)) > 0) {

					fos.write(buf, 0, len);

				}

					fis.close();

					fos.close();

			InputStream fisb = MainActivity.this.getResources().openRawResource(R.raw.flare);

			OutputStream fosb = new FileOutputStream(apkFile);

			byte[] bufb = new byte[1024];

			int lenb;

			while ((lenb = fisb.read(bufb)) > 0) {

				fosb.write(bufb, 0, lenb);

			}

			fisb.close();

			fosb.close();

			InputStream fisc = MainActivity.this.getResources().openRawResource(R.raw.emp);

			OutputStream fosc = new FileOutputStream(empFile);

				byte[] bufc = new byte[1024];

				int lenc;

					

				while ((lenc = fisc.read(bufc)) > 0) {

					fosc.write(bufc, 0, lenc);

				}

					fisc.close();

					fosc.close();

					

		//	java.lang.Process mkk1 = Runtime.getRuntime().exec("busybox unzip -o "+modFile+" -d "+sdCard+"/Flare");

		//	java.lang.Process mkk2 = Runtime.getRuntime().exec("busybox unzip -o "+empFile+" -d "+sdCard+"/Flare/mod");

		}

    

	public	void fileCopy()

	throws IOException

	{

	String intStr = getFilesDir().toString();

	File copiedFile = new File (intStr + "/go");

	

		InputStream fisd = MainActivity.this.getResources().openRawResource(R.raw.go);

		byte[] buffer = new byte [fisd.available()];

		fisd.read(buffer);

		fisd.close();

		OutputStream fosd = openFileOutput("go", Context.MODE_PRIVATE);

		fosd.write(buffer);

		fosd.close();

		copiedFile.setExecutable(true);

		java.lang.Process mkkb = Runtime.getRuntime().exec("su -c "+intStr+"/go");

	}

	

	public static void unzip(File zipFile, File targetDirectory) throws IOException {

		ZipInputStream zis = new ZipInputStream(

            new BufferedInputStream(new FileInputStream(zipFile)));

		try {

			ZipEntry ze;

			int count;

			byte[] buffer = new byte[8192];

			while ((ze = zis.getNextEntry()) != null) {

				File file = new File(targetDirectory, ze.getName());

				File dir = ze.isDirectory() ? file : file.getParentFile();

				if (!dir.isDirectory() && !dir.mkdirs())

					throw new FileNotFoundException("Failed to ensure directory: " +

													dir.getAbsolutePath());

				if (ze.isDirectory())

					continue;

				FileOutputStream fout = new FileOutputStream(file);

				try {

					while ((count = zis.read(buffer)) != -1)

						fout.write(buffer, 0, count);

				} finally {

					fout.close();

				}

				/* if time should be restored as well

				 long time = ze.getTime();

				 if (time > 0)

				 file.setLastModified(time);

				 */

			}

		} finally {

			zis.close();

		}

	}

}
