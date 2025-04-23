package com.podcastr2.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PodcastrDatabase_Impl extends PodcastrDatabase {
  private volatile PodcastDao _podcastDao;

  private volatile EpisodeDao _episodeDao;

  private volatile AdSegmentDao _adSegmentDao;

  private volatile DownloadTaskDao _downloadTaskDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `podcasts` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `author` TEXT NOT NULL, `imageUrl` TEXT NOT NULL, `feedUrl` TEXT NOT NULL, `website` TEXT NOT NULL, `isYouTube` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `lastChecked` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `episodes` (`id` TEXT NOT NULL, `podcastId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `audioUrl` TEXT NOT NULL, `imageUrl` TEXT NOT NULL, `duration` INTEGER NOT NULL, `publishDate` INTEGER NOT NULL, `isDownloaded` INTEGER NOT NULL, `downloadPath` TEXT, `isTranscribed` INTEGER NOT NULL, `transcriptionPath` TEXT, `isAnalyzed` INTEGER NOT NULL, `lastPlayedPosition` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `ad_segments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `episodeId` TEXT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `confidence` REAL NOT NULL, `transcriptSnippet` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `download_tasks` (`episodeId` TEXT NOT NULL, `status` TEXT NOT NULL, `progress` INTEGER NOT NULL, `errorMessage` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`episodeId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2df6aa97a9ce83ac5635e2aa661d081d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `podcasts`");
        db.execSQL("DROP TABLE IF EXISTS `episodes`");
        db.execSQL("DROP TABLE IF EXISTS `ad_segments`");
        db.execSQL("DROP TABLE IF EXISTS `download_tasks`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPodcasts = new HashMap<String, TableInfo.Column>(10);
        _columnsPodcasts.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("author", new TableInfo.Column("author", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("feedUrl", new TableInfo.Column("feedUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("website", new TableInfo.Column("website", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("isYouTube", new TableInfo.Column("isYouTube", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("lastUpdated", new TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPodcasts.put("lastChecked", new TableInfo.Column("lastChecked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPodcasts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPodcasts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPodcasts = new TableInfo("podcasts", _columnsPodcasts, _foreignKeysPodcasts, _indicesPodcasts);
        final TableInfo _existingPodcasts = TableInfo.read(db, "podcasts");
        if (!_infoPodcasts.equals(_existingPodcasts)) {
          return new RoomOpenHelper.ValidationResult(false, "podcasts(com.podcastr2.data.model.Podcast).\n"
                  + " Expected:\n" + _infoPodcasts + "\n"
                  + " Found:\n" + _existingPodcasts);
        }
        final HashMap<String, TableInfo.Column> _columnsEpisodes = new HashMap<String, TableInfo.Column>(15);
        _columnsEpisodes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("podcastId", new TableInfo.Column("podcastId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("audioUrl", new TableInfo.Column("audioUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("publishDate", new TableInfo.Column("publishDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("isDownloaded", new TableInfo.Column("isDownloaded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("downloadPath", new TableInfo.Column("downloadPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("isTranscribed", new TableInfo.Column("isTranscribed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("transcriptionPath", new TableInfo.Column("transcriptionPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("isAnalyzed", new TableInfo.Column("isAnalyzed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("lastPlayedPosition", new TableInfo.Column("lastPlayedPosition", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEpisodes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEpisodes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEpisodes = new TableInfo("episodes", _columnsEpisodes, _foreignKeysEpisodes, _indicesEpisodes);
        final TableInfo _existingEpisodes = TableInfo.read(db, "episodes");
        if (!_infoEpisodes.equals(_existingEpisodes)) {
          return new RoomOpenHelper.ValidationResult(false, "episodes(com.podcastr2.data.model.Episode).\n"
                  + " Expected:\n" + _infoEpisodes + "\n"
                  + " Found:\n" + _existingEpisodes);
        }
        final HashMap<String, TableInfo.Column> _columnsAdSegments = new HashMap<String, TableInfo.Column>(6);
        _columnsAdSegments.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdSegments.put("episodeId", new TableInfo.Column("episodeId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdSegments.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdSegments.put("endTime", new TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdSegments.put("confidence", new TableInfo.Column("confidence", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdSegments.put("transcriptSnippet", new TableInfo.Column("transcriptSnippet", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAdSegments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAdSegments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAdSegments = new TableInfo("ad_segments", _columnsAdSegments, _foreignKeysAdSegments, _indicesAdSegments);
        final TableInfo _existingAdSegments = TableInfo.read(db, "ad_segments");
        if (!_infoAdSegments.equals(_existingAdSegments)) {
          return new RoomOpenHelper.ValidationResult(false, "ad_segments(com.podcastr2.data.model.AdSegment).\n"
                  + " Expected:\n" + _infoAdSegments + "\n"
                  + " Found:\n" + _existingAdSegments);
        }
        final HashMap<String, TableInfo.Column> _columnsDownloadTasks = new HashMap<String, TableInfo.Column>(6);
        _columnsDownloadTasks.put("episodeId", new TableInfo.Column("episodeId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloadTasks.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloadTasks.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloadTasks.put("errorMessage", new TableInfo.Column("errorMessage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloadTasks.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloadTasks.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDownloadTasks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDownloadTasks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDownloadTasks = new TableInfo("download_tasks", _columnsDownloadTasks, _foreignKeysDownloadTasks, _indicesDownloadTasks);
        final TableInfo _existingDownloadTasks = TableInfo.read(db, "download_tasks");
        if (!_infoDownloadTasks.equals(_existingDownloadTasks)) {
          return new RoomOpenHelper.ValidationResult(false, "download_tasks(com.podcastr2.data.model.DownloadTask).\n"
                  + " Expected:\n" + _infoDownloadTasks + "\n"
                  + " Found:\n" + _existingDownloadTasks);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "2df6aa97a9ce83ac5635e2aa661d081d", "78327b8bd2a9d37c379722e7cd3f1f71");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "podcasts","episodes","ad_segments","download_tasks");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `podcasts`");
      _db.execSQL("DELETE FROM `episodes`");
      _db.execSQL("DELETE FROM `ad_segments`");
      _db.execSQL("DELETE FROM `download_tasks`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PodcastDao.class, PodcastDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EpisodeDao.class, EpisodeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AdSegmentDao.class, AdSegmentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DownloadTaskDao.class, DownloadTaskDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PodcastDao podcastDao() {
    if (_podcastDao != null) {
      return _podcastDao;
    } else {
      synchronized(this) {
        if(_podcastDao == null) {
          _podcastDao = new PodcastDao_Impl(this);
        }
        return _podcastDao;
      }
    }
  }

  @Override
  public EpisodeDao episodeDao() {
    if (_episodeDao != null) {
      return _episodeDao;
    } else {
      synchronized(this) {
        if(_episodeDao == null) {
          _episodeDao = new EpisodeDao_Impl(this);
        }
        return _episodeDao;
      }
    }
  }

  @Override
  public AdSegmentDao adSegmentDao() {
    if (_adSegmentDao != null) {
      return _adSegmentDao;
    } else {
      synchronized(this) {
        if(_adSegmentDao == null) {
          _adSegmentDao = new AdSegmentDao_Impl(this);
        }
        return _adSegmentDao;
      }
    }
  }

  @Override
  public DownloadTaskDao downloadTaskDao() {
    if (_downloadTaskDao != null) {
      return _downloadTaskDao;
    } else {
      synchronized(this) {
        if(_downloadTaskDao == null) {
          _downloadTaskDao = new DownloadTaskDao_Impl(this);
        }
        return _downloadTaskDao;
      }
    }
  }
}
