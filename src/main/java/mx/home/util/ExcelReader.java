package mx.home.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {

	/**
	 * Es esta otra alternativa de lectura del archivo de Excel, usando al api
	 * jxl. Basado en proyecto de seguros inbursa de CGB
	 * 
	 * @throws IOException
	 * @throws BiffException
	 */
	public List<List<String>> leerArchivoExcel(final InputStream inputFile)
			throws BiffException, IOException {
		final List<List<String>> listaFilas = new ArrayList<List<String>>();
		final Workbook workbook = getWorkbook(inputFile);
		final Sheet sheet = workbook.getSheet(0);
		for (int row = 0; row < sheet.getRows(); row++) {
			readRow(listaFilas, sheet, row);
		}
		return listaFilas;
	}

	private Workbook getWorkbook(final InputStream inputFile)
			throws BiffException, IOException {
		Workbook workbook = null; // NOPMD by Cesar on 11/08/11 0:44
		try {
			workbook = Workbook.getWorkbook(inputFile);
		} catch (BiffException ex) {
			LOG.error("El formato de archivo especificado no es soportado ");
			throw ex;
		} catch (IOException ex) {
			LOG.error("In getWorkbook()");
			throw ex;
		}
		return workbook;
	}

	private void readRow(final List<List<String>> listaFilas,
			final Sheet sheet, final int row) {
		final Cell[] renglon = sheet.getRow(row);
		final List<String> listaColumna = new ArrayList<String>();
		// Integer numData = 0; // NOPMD by Cesar on 11/08/11 2:29
		for (int colum = 0; colum < renglon.length; colum++) {
			final Cell cell = renglon[colum];
			final String cellContent = cell.getContents();
			LOG.trace("cellContent: " + cellContent);
			LOG.trace("cellContent is empty: " + cellContent.isEmpty());
			if (!cellContent.isEmpty()) {
				// listaColumna.add(numData++, cellContent);
				listaColumna.add("[" + cell.getRow() + "," + cell.getColumn()
						+ "]" + cellContent);
			}
		}
		listaFilas.add(row, listaColumna);
	}

	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(ExcelReader.class);
	}

	public void leerArchivoExcel(String string) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Falta por imple,emntrar!!!");
	}

}
