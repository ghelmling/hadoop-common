/**
 *
 * Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * @author: Sriram Rao (Kosmix Corp.)
 * 
 * Provide the implementation of KFS which turn into calls to KfsAccess.
 */

package org.apache.hadoop.fs.kfs;

import java.io.*;
import java.net.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Progressable;

import org.kosmix.kosmosfs.access.KfsAccess;

class KFSImpl implements IFSImpl {
    private KfsAccess kfsAccess = null;

    public KFSImpl(String metaServerHost, int metaServerPort) throws IOException {
        kfsAccess = new KfsAccess(metaServerHost, metaServerPort);
    }

    public boolean exists(String path) throws IOException {
        return kfsAccess.kfs_exists(path);
    }

    public boolean isDirectory(String path) throws IOException {
        return kfsAccess.kfs_isDirectory(path);
    }

    public boolean isFile(String path) throws IOException {
        return kfsAccess.kfs_isFile(path);
    }

    public String[] readdir(String path) throws IOException {
        return kfsAccess.kfs_readdir(path);
    }

    public int mkdirs(String path) throws IOException {
        return kfsAccess.kfs_mkdirs(path);
    }

    public int rename(String source, String dest) throws IOException {
        return kfsAccess.kfs_rename(source, dest);
    }

    public int rmdir(String path) throws IOException {
        return kfsAccess.kfs_rmdir(path);
    }

    public int remove(String path) throws IOException {
        return kfsAccess.kfs_remove(path);
    }

    public long filesize(String path) throws IOException {
        return kfsAccess.kfs_filesize(path);
    }

    public short getReplication(String path) throws IOException {
        return kfsAccess.kfs_getReplication(path);
    }

    public short setReplication(String path, short replication) throws IOException {
        return kfsAccess.kfs_setReplication(path, replication);
    }

    public String[][] getDataLocation(String path, long start, long len) throws IOException {
        return kfsAccess.kfs_getDataLocation(path, start, len);
    }

    public long getModificationTime(String path) throws IOException {
        // Supporting this API requires changes to the Java-side of
        // the KFS client API.  For now, return 0; in the next rev of
        // KFS, we'll update the Java API.
        return 0;
    }

    public FSDataOutputStream create(String path, short replication, int bufferSize) throws IOException {
        return new FSDataOutputStream(new KFSOutputStream(kfsAccess, path, replication));
    }

    public FSDataInputStream open(String path, int bufferSize) throws IOException {
        return new FSDataInputStream(new KFSInputStream(kfsAccess, path));
    }
};