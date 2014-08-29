package org.dynamicus.khanasa;

/*$Id$*/
/*
 ******************************************************************************
 *                                                                            *
 *              (c) Copyright 2003 Laszlo Szathmary                           *
 *                                                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation; either version 2.1 of the License, or        *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public    *
 * License for more details.                                                  *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program; if not, write to the Free Software Foundation,    *
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.                              *
 *                                                                            *
 ******************************************************************************
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * @author Laszlo Szathmary (<a href="jabba.laci@gmail.com">jabba.laci@gmail.com</a>)
 * @version v0.1, 2003/12/04 (December) -- first release
 */
public class GraphViz {
    private static final Logger log = LoggerFactory.getLogger(KhanasaDiagramCreator.class);
    /**
     * The dir. where temporary files will be created.
     */
    private static String TEMP_DIR = "/tmp";    // Linux

    /**
     * Where is your dot program located? It will be called externally.
     */
    private static String DOT = "/usr/bin/dot";    // Linux

    /**
     * Constructor: creates a new org.dynamicus.khanasa.GraphViz object that will contain
     * a graph.
     */
    public GraphViz() {
    }

    /**
     * Returns the graph as an image in binary format.
     *
     * @param dotSource Source of the graph to be drawn.
     * @param type       Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
     * @return A byte array containing the image of the graph.
     */
    public byte[] getGraph(String dotSource, String type) {
        File dot;
        byte[] imgStream = null;

        try {
            dot = writeDotSourceToFile(dotSource);
            if (dot != null) {
                imgStream = getImageStream(dot, type);
                if (!dot.delete()) {
                    log.error("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
                }

                return imgStream;
            }
            return new byte[0];
        } catch (java.io.IOException ioe) {
            log.error("Trouble ", ioe);
            return new byte[0];
        }
    }

    /**
     * Writes the graph's image in a file.
     *
     * @param img A byte array containing the image of the graph.
     * @param to  A File object to where we want to write.
     * @return Success: 1, Failure: -1
     */
    public int writeGraphToFile(byte[] img, File to) {
        try {
            FileOutputStream fos = new FileOutputStream(to);
            fos.write(img);
            fos.close();
        } catch (java.io.IOException ioe) {
            log.error("Trouble", ioe);
            return -1;
        }
        return 1;
    }

    /**
     * It will call the external dot program, and return the image in
     * binary format.
     *
     * @param dot  Source of the graph (in dot language).
     * @param type Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
     * @return The image of the graph in .gif format.
     */
    private byte[] getImageStream(File dot, String type) {
        File img;
        byte[] imgStream = null;

        try {
            img = File.createTempFile("graph_", "." + type, new File(GraphViz.TEMP_DIR));
            Runtime rt = Runtime.getRuntime();

            // patch by Mike Chenault
            String[] args = {DOT, "-T" + type, dot.getAbsolutePath(), "-o", img.getAbsolutePath()};
            Process p = rt.exec(args);

            p.waitFor();

            FileInputStream in = new FileInputStream(img.getAbsolutePath());
            imgStream = new byte[in.available()];
            in.read(imgStream);
            // Close it if we need to
            if (in != null) {
                in.close();
            }

            if (!img.delete()) {
                log.error("Warning: " + img.getAbsolutePath() + " could not be deleted!");
            }

        } catch (java.io.IOException ioe) {
            log.error("Error:    in I/O processing of tempfile in dir " + GraphViz.TEMP_DIR + "\n");
            log.error(" or in calling external command", ioe);
        } catch (java.lang.InterruptedException ie) {
            log.error("Error: the execution of the external program was interrupted", ie);
        }

        return imgStream;
    }

    /**
     * Writes the source of the graph in a file, and returns the written file
     * as a File object.
     *
     * @param str Source of the graph (in dot language).
     * @return The file (as a File object) that contains the source of the graph.
     */
    private File writeDotSourceToFile(String str) throws java.io.IOException {
        File temp;
        try {
            temp = File.createTempFile("graph_", ".dot.tmp", new File(GraphViz.TEMP_DIR));
            FileWriter fout = new FileWriter(temp);
            fout.write(str);
            fout.close();
        } catch (Exception e) {
            log.error("Error: I/O error while writing the dot source to temp file!", e);
            return null;
        }
        return temp;
    }

}

