package com.podcastr2.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.podcastr2.data.model.Episode;
import com.podcastr2.data.model.Podcast;
import com.podcastr2.data.model.PodcastWithEpisodes;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PodcastDao_Impl implements PodcastDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Podcast> __insertionAdapterOfPodcast;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Podcast> __deletionAdapterOfPodcast;

  private final EntityDeletionOrUpdateAdapter<Podcast> __updateAdapterOfPodcast;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastChecked;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastUpdated;

  public PodcastDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPodcast = new EntityInsertionAdapter<Podcast>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `podcasts` (`id`,`title`,`description`,`author`,`imageUrl`,`feedUrl`,`website`,`isYouTube`,`lastUpdated`,`lastChecked`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Podcast entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getAuthor());
        statement.bindString(5, entity.getImageUrl());
        statement.bindString(6, entity.getFeedUrl());
        statement.bindString(7, entity.getWebsite());
        final int _tmp = entity.isYouTube() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getLastUpdated());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getLastChecked());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfPodcast = new EntityDeletionOrUpdateAdapter<Podcast>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `podcasts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Podcast entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfPodcast = new EntityDeletionOrUpdateAdapter<Podcast>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `podcasts` SET `id` = ?,`title` = ?,`description` = ?,`author` = ?,`imageUrl` = ?,`feedUrl` = ?,`website` = ?,`isYouTube` = ?,`lastUpdated` = ?,`lastChecked` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Podcast entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getAuthor());
        statement.bindString(5, entity.getImageUrl());
        statement.bindString(6, entity.getFeedUrl());
        statement.bindString(7, entity.getWebsite());
        final int _tmp = entity.isYouTube() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getLastUpdated());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getLastChecked());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp_2);
        }
        statement.bindString(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM podcasts WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastChecked = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE podcasts SET lastChecked = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastUpdated = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE podcasts SET lastUpdated = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Podcast podcast, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPodcast.insert(podcast);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Podcast> podcasts,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPodcast.insert(podcasts);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Podcast podcast, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPodcast.handle(podcast);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Podcast podcast, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPodcast.handle(podcast);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String podcastId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, podcastId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastChecked(final String podcastId, final long lastChecked,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastChecked.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lastChecked);
        _argIndex = 2;
        _stmt.bindString(_argIndex, podcastId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateLastChecked.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastUpdated(final String podcastId, final long lastUpdated,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastUpdated.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lastUpdated);
        _argIndex = 2;
        _stmt.bindString(_argIndex, podcastId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateLastUpdated.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Podcast>> getAllPodcasts() {
    final String _sql = "SELECT * FROM podcasts ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"podcasts"}, new Callable<List<Podcast>>() {
      @Override
      @NonNull
      public List<Podcast> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfFeedUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "feedUrl");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfIsYouTube = CursorUtil.getColumnIndexOrThrow(_cursor, "isYouTube");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLastChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "lastChecked");
          final List<Podcast> _result = new ArrayList<Podcast>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Podcast _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpFeedUrl;
            _tmpFeedUrl = _cursor.getString(_cursorIndexOfFeedUrl);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final boolean _tmpIsYouTube;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsYouTube);
            _tmpIsYouTube = _tmp != 0;
            final Date _tmpLastUpdated;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_2;
            }
            final Date _tmpLastChecked;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfLastChecked)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfLastChecked);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastChecked = _tmp_4;
            }
            _item = new Podcast(_tmpId,_tmpTitle,_tmpDescription,_tmpAuthor,_tmpImageUrl,_tmpFeedUrl,_tmpWebsite,_tmpIsYouTube,_tmpLastUpdated,_tmpLastChecked);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Podcast> getPodcastById(final String podcastId) {
    final String _sql = "SELECT * FROM podcasts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, podcastId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"podcasts"}, new Callable<Podcast>() {
      @Override
      @Nullable
      public Podcast call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfFeedUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "feedUrl");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfIsYouTube = CursorUtil.getColumnIndexOrThrow(_cursor, "isYouTube");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLastChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "lastChecked");
          final Podcast _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpFeedUrl;
            _tmpFeedUrl = _cursor.getString(_cursorIndexOfFeedUrl);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final boolean _tmpIsYouTube;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsYouTube);
            _tmpIsYouTube = _tmp != 0;
            final Date _tmpLastUpdated;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_2;
            }
            final Date _tmpLastChecked;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfLastChecked)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfLastChecked);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastChecked = _tmp_4;
            }
            _result = new Podcast(_tmpId,_tmpTitle,_tmpDescription,_tmpAuthor,_tmpImageUrl,_tmpFeedUrl,_tmpWebsite,_tmpIsYouTube,_tmpLastUpdated,_tmpLastChecked);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPodcastByFeedUrl(final String feedUrl,
      final Continuation<? super Podcast> $completion) {
    final String _sql = "SELECT * FROM podcasts WHERE feedUrl = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, feedUrl);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Podcast>() {
      @Override
      @Nullable
      public Podcast call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfFeedUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "feedUrl");
          final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
          final int _cursorIndexOfIsYouTube = CursorUtil.getColumnIndexOrThrow(_cursor, "isYouTube");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfLastChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "lastChecked");
          final Podcast _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpFeedUrl;
            _tmpFeedUrl = _cursor.getString(_cursorIndexOfFeedUrl);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            final boolean _tmpIsYouTube;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsYouTube);
            _tmpIsYouTube = _tmp != 0;
            final Date _tmpLastUpdated;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_2;
            }
            final Date _tmpLastChecked;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfLastChecked)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfLastChecked);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastChecked = _tmp_4;
            }
            _result = new Podcast(_tmpId,_tmpTitle,_tmpDescription,_tmpAuthor,_tmpImageUrl,_tmpFeedUrl,_tmpWebsite,_tmpIsYouTube,_tmpLastUpdated,_tmpLastChecked);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<PodcastWithEpisodes> getPodcastWithEpisodes(final String podcastId) {
    final String _sql = "SELECT * FROM podcasts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, podcastId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"episodes",
        "podcasts"}, new Callable<PodcastWithEpisodes>() {
      @Override
      @Nullable
      public PodcastWithEpisodes call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
            final int _cursorIndexOfFeedUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "feedUrl");
            final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
            final int _cursorIndexOfIsYouTube = CursorUtil.getColumnIndexOrThrow(_cursor, "isYouTube");
            final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
            final int _cursorIndexOfLastChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "lastChecked");
            final ArrayMap<String, ArrayList<Episode>> _collectionEpisodes = new ArrayMap<String, ArrayList<Episode>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionEpisodes.containsKey(_tmpKey)) {
                _collectionEpisodes.put(_tmpKey, new ArrayList<Episode>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipepisodesAscomPodcastr2DataModelEpisode(_collectionEpisodes);
            final PodcastWithEpisodes _result;
            if (_cursor.moveToFirst()) {
              final Podcast _tmpPodcast;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpTitle;
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              final String _tmpDescription;
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              final String _tmpAuthor;
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
              final String _tmpImageUrl;
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              final String _tmpFeedUrl;
              _tmpFeedUrl = _cursor.getString(_cursorIndexOfFeedUrl);
              final String _tmpWebsite;
              _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
              final boolean _tmpIsYouTube;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsYouTube);
              _tmpIsYouTube = _tmp != 0;
              final Date _tmpLastUpdated;
              final Long _tmp_1;
              if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
                _tmp_1 = null;
              } else {
                _tmp_1 = _cursor.getLong(_cursorIndexOfLastUpdated);
              }
              final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
              if (_tmp_2 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
              } else {
                _tmpLastUpdated = _tmp_2;
              }
              final Date _tmpLastChecked;
              final Long _tmp_3;
              if (_cursor.isNull(_cursorIndexOfLastChecked)) {
                _tmp_3 = null;
              } else {
                _tmp_3 = _cursor.getLong(_cursorIndexOfLastChecked);
              }
              final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
              if (_tmp_4 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
              } else {
                _tmpLastChecked = _tmp_4;
              }
              _tmpPodcast = new Podcast(_tmpId,_tmpTitle,_tmpDescription,_tmpAuthor,_tmpImageUrl,_tmpFeedUrl,_tmpWebsite,_tmpIsYouTube,_tmpLastUpdated,_tmpLastChecked);
              final ArrayList<Episode> _tmpEpisodesCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpEpisodesCollection = _collectionEpisodes.get(_tmpKey_1);
              _result = new PodcastWithEpisodes(_tmpPodcast,_tmpEpisodesCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PodcastWithEpisodes>> getAllPodcastsWithEpisodes() {
    final String _sql = "SELECT * FROM podcasts ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"episodes",
        "podcasts"}, new Callable<List<PodcastWithEpisodes>>() {
      @Override
      @NonNull
      public List<PodcastWithEpisodes> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
            final int _cursorIndexOfFeedUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "feedUrl");
            final int _cursorIndexOfWebsite = CursorUtil.getColumnIndexOrThrow(_cursor, "website");
            final int _cursorIndexOfIsYouTube = CursorUtil.getColumnIndexOrThrow(_cursor, "isYouTube");
            final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
            final int _cursorIndexOfLastChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "lastChecked");
            final ArrayMap<String, ArrayList<Episode>> _collectionEpisodes = new ArrayMap<String, ArrayList<Episode>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionEpisodes.containsKey(_tmpKey)) {
                _collectionEpisodes.put(_tmpKey, new ArrayList<Episode>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipepisodesAscomPodcastr2DataModelEpisode(_collectionEpisodes);
            final List<PodcastWithEpisodes> _result = new ArrayList<PodcastWithEpisodes>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final PodcastWithEpisodes _item;
              final Podcast _tmpPodcast;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpTitle;
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              final String _tmpDescription;
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              final String _tmpAuthor;
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
              final String _tmpImageUrl;
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              final String _tmpFeedUrl;
              _tmpFeedUrl = _cursor.getString(_cursorIndexOfFeedUrl);
              final String _tmpWebsite;
              _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
              final boolean _tmpIsYouTube;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsYouTube);
              _tmpIsYouTube = _tmp != 0;
              final Date _tmpLastUpdated;
              final Long _tmp_1;
              if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
                _tmp_1 = null;
              } else {
                _tmp_1 = _cursor.getLong(_cursorIndexOfLastUpdated);
              }
              final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
              if (_tmp_2 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
              } else {
                _tmpLastUpdated = _tmp_2;
              }
              final Date _tmpLastChecked;
              final Long _tmp_3;
              if (_cursor.isNull(_cursorIndexOfLastChecked)) {
                _tmp_3 = null;
              } else {
                _tmp_3 = _cursor.getLong(_cursorIndexOfLastChecked);
              }
              final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
              if (_tmp_4 == null) {
                throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
              } else {
                _tmpLastChecked = _tmp_4;
              }
              _tmpPodcast = new Podcast(_tmpId,_tmpTitle,_tmpDescription,_tmpAuthor,_tmpImageUrl,_tmpFeedUrl,_tmpWebsite,_tmpIsYouTube,_tmpLastUpdated,_tmpLastChecked);
              final ArrayList<Episode> _tmpEpisodesCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpEpisodesCollection = _collectionEpisodes.get(_tmpKey_1);
              _item = new PodcastWithEpisodes(_tmpPodcast,_tmpEpisodesCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipepisodesAscomPodcastr2DataModelEpisode(
      @NonNull final ArrayMap<String, ArrayList<Episode>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipepisodesAscomPodcastr2DataModelEpisode(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`podcastId`,`title`,`description`,`audioUrl`,`imageUrl`,`duration`,`publishDate`,`isDownloaded`,`downloadPath`,`isTranscribed`,`transcriptionPath`,`isAnalyzed`,`lastPlayedPosition`,`isCompleted` FROM `episodes` WHERE `podcastId` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "podcastId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfPodcastId = 1;
      final int _cursorIndexOfTitle = 2;
      final int _cursorIndexOfDescription = 3;
      final int _cursorIndexOfAudioUrl = 4;
      final int _cursorIndexOfImageUrl = 5;
      final int _cursorIndexOfDuration = 6;
      final int _cursorIndexOfPublishDate = 7;
      final int _cursorIndexOfIsDownloaded = 8;
      final int _cursorIndexOfDownloadPath = 9;
      final int _cursorIndexOfIsTranscribed = 10;
      final int _cursorIndexOfTranscriptionPath = 11;
      final int _cursorIndexOfIsAnalyzed = 12;
      final int _cursorIndexOfLastPlayedPosition = 13;
      final int _cursorIndexOfIsCompleted = 14;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        final ArrayList<Episode> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Episode _item_1;
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
          final String _tmpPodcastId;
          _tmpPodcastId = _cursor.getString(_cursorIndexOfPodcastId);
          final String _tmpTitle;
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
          final String _tmpDescription;
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
          final String _tmpAudioUrl;
          _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
          final String _tmpImageUrl;
          _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
          final long _tmpDuration;
          _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
          final Date _tmpPublishDate;
          final Long _tmp;
          if (_cursor.isNull(_cursorIndexOfPublishDate)) {
            _tmp = null;
          } else {
            _tmp = _cursor.getLong(_cursorIndexOfPublishDate);
          }
          final Date _tmp_1 = __converters.fromTimestamp(_tmp);
          if (_tmp_1 == null) {
            throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
          } else {
            _tmpPublishDate = _tmp_1;
          }
          final boolean _tmpIsDownloaded;
          final int _tmp_2;
          _tmp_2 = _cursor.getInt(_cursorIndexOfIsDownloaded);
          _tmpIsDownloaded = _tmp_2 != 0;
          final String _tmpDownloadPath;
          if (_cursor.isNull(_cursorIndexOfDownloadPath)) {
            _tmpDownloadPath = null;
          } else {
            _tmpDownloadPath = _cursor.getString(_cursorIndexOfDownloadPath);
          }
          final boolean _tmpIsTranscribed;
          final int _tmp_3;
          _tmp_3 = _cursor.getInt(_cursorIndexOfIsTranscribed);
          _tmpIsTranscribed = _tmp_3 != 0;
          final String _tmpTranscriptionPath;
          if (_cursor.isNull(_cursorIndexOfTranscriptionPath)) {
            _tmpTranscriptionPath = null;
          } else {
            _tmpTranscriptionPath = _cursor.getString(_cursorIndexOfTranscriptionPath);
          }
          final boolean _tmpIsAnalyzed;
          final int _tmp_4;
          _tmp_4 = _cursor.getInt(_cursorIndexOfIsAnalyzed);
          _tmpIsAnalyzed = _tmp_4 != 0;
          final long _tmpLastPlayedPosition;
          _tmpLastPlayedPosition = _cursor.getLong(_cursorIndexOfLastPlayedPosition);
          final boolean _tmpIsCompleted;
          final int _tmp_5;
          _tmp_5 = _cursor.getInt(_cursorIndexOfIsCompleted);
          _tmpIsCompleted = _tmp_5 != 0;
          _item_1 = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
