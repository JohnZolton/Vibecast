package com.podcastr2.data.db;

import android.database.Cursor;
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
import com.podcastr2.data.model.AdSegment;
import com.podcastr2.data.model.Episode;
import com.podcastr2.data.model.EpisodeWithAdSegments;
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
public final class EpisodeDao_Impl implements EpisodeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Episode> __insertionAdapterOfEpisode;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Episode> __deletionAdapterOfEpisode;

  private final EntityDeletionOrUpdateAdapter<Episode> __updateAdapterOfEpisode;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByPodcastId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDownloadStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTranscriptionStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateAnalysisStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePlaybackPosition;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCompletionStatus;

  public EpisodeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEpisode = new EntityInsertionAdapter<Episode>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `episodes` (`id`,`podcastId`,`title`,`description`,`audioUrl`,`imageUrl`,`duration`,`publishDate`,`isDownloaded`,`downloadPath`,`isTranscribed`,`transcriptionPath`,`isAnalyzed`,`lastPlayedPosition`,`isCompleted`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Episode entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getPodcastId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getAudioUrl());
        statement.bindString(6, entity.getImageUrl());
        statement.bindLong(7, entity.getDuration());
        final Long _tmp = __converters.dateToTimestamp(entity.getPublishDate());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp);
        }
        final int _tmp_1 = entity.isDownloaded() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        if (entity.getDownloadPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getDownloadPath());
        }
        final int _tmp_2 = entity.isTranscribed() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        if (entity.getTranscriptionPath() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTranscriptionPath());
        }
        final int _tmp_3 = entity.isAnalyzed() ? 1 : 0;
        statement.bindLong(13, _tmp_3);
        statement.bindLong(14, entity.getLastPlayedPosition());
        final int _tmp_4 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(15, _tmp_4);
      }
    };
    this.__deletionAdapterOfEpisode = new EntityDeletionOrUpdateAdapter<Episode>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `episodes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Episode entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfEpisode = new EntityDeletionOrUpdateAdapter<Episode>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `episodes` SET `id` = ?,`podcastId` = ?,`title` = ?,`description` = ?,`audioUrl` = ?,`imageUrl` = ?,`duration` = ?,`publishDate` = ?,`isDownloaded` = ?,`downloadPath` = ?,`isTranscribed` = ?,`transcriptionPath` = ?,`isAnalyzed` = ?,`lastPlayedPosition` = ?,`isCompleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Episode entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getPodcastId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getAudioUrl());
        statement.bindString(6, entity.getImageUrl());
        statement.bindLong(7, entity.getDuration());
        final Long _tmp = __converters.dateToTimestamp(entity.getPublishDate());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp);
        }
        final int _tmp_1 = entity.isDownloaded() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        if (entity.getDownloadPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getDownloadPath());
        }
        final int _tmp_2 = entity.isTranscribed() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        if (entity.getTranscriptionPath() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTranscriptionPath());
        }
        final int _tmp_3 = entity.isAnalyzed() ? 1 : 0;
        statement.bindLong(13, _tmp_3);
        statement.bindLong(14, entity.getLastPlayedPosition());
        final int _tmp_4 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(15, _tmp_4);
        statement.bindString(16, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM episodes WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByPodcastId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM episodes WHERE podcastId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDownloadStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE episodes SET isDownloaded = ?, downloadPath = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTranscriptionStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE episodes SET isTranscribed = ?, transcriptionPath = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateAnalysisStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE episodes SET isAnalyzed = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePlaybackPosition = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE episodes SET lastPlayedPosition = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCompletionStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE episodes SET isCompleted = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Episode episode, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEpisode.insert(episode);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Episode> episodes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEpisode.insert(episodes);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Episode episode, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEpisode.handle(episode);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Episode episode, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEpisode.handle(episode);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String episodeId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, episodeId);
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
  public Object deleteByPodcastId(final String podcastId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByPodcastId.acquire();
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
          __preparedStmtOfDeleteByPodcastId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDownloadStatus(final String episodeId, final boolean isDownloaded,
      final String downloadPath, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDownloadStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isDownloaded ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (downloadPath == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, downloadPath);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, episodeId);
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
          __preparedStmtOfUpdateDownloadStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTranscriptionStatus(final String episodeId, final boolean isTranscribed,
      final String transcriptionPath, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTranscriptionStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isTranscribed ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (transcriptionPath == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, transcriptionPath);
        }
        _argIndex = 3;
        _stmt.bindString(_argIndex, episodeId);
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
          __preparedStmtOfUpdateTranscriptionStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAnalysisStatus(final String episodeId, final boolean isAnalyzed,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateAnalysisStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isAnalyzed ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, episodeId);
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
          __preparedStmtOfUpdateAnalysisStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePlaybackPosition(final String episodeId, final long position,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePlaybackPosition.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, position);
        _argIndex = 2;
        _stmt.bindString(_argIndex, episodeId);
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
          __preparedStmtOfUpdatePlaybackPosition.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCompletionStatus(final String episodeId, final boolean isCompleted,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCompletionStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isCompleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, episodeId);
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
          __preparedStmtOfUpdateCompletionStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Episode>> getAllEpisodes() {
    final String _sql = "SELECT * FROM episodes ORDER BY publishDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"episodes"}, new Callable<List<Episode>>() {
      @Override
      @NonNull
      public List<Episode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPodcastId = CursorUtil.getColumnIndexOrThrow(_cursor, "podcastId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPublishDate = CursorUtil.getColumnIndexOrThrow(_cursor, "publishDate");
          final int _cursorIndexOfIsDownloaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isDownloaded");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsTranscribed = CursorUtil.getColumnIndexOrThrow(_cursor, "isTranscribed");
          final int _cursorIndexOfTranscriptionPath = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptionPath");
          final int _cursorIndexOfIsAnalyzed = CursorUtil.getColumnIndexOrThrow(_cursor, "isAnalyzed");
          final int _cursorIndexOfLastPlayedPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedPosition");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Episode> _result = new ArrayList<Episode>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Episode _item;
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
            _item = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
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
  public Flow<List<Episode>> getEpisodesByPodcastId(final String podcastId) {
    final String _sql = "SELECT * FROM episodes WHERE podcastId = ? ORDER BY publishDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, podcastId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"episodes"}, new Callable<List<Episode>>() {
      @Override
      @NonNull
      public List<Episode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPodcastId = CursorUtil.getColumnIndexOrThrow(_cursor, "podcastId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPublishDate = CursorUtil.getColumnIndexOrThrow(_cursor, "publishDate");
          final int _cursorIndexOfIsDownloaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isDownloaded");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsTranscribed = CursorUtil.getColumnIndexOrThrow(_cursor, "isTranscribed");
          final int _cursorIndexOfTranscriptionPath = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptionPath");
          final int _cursorIndexOfIsAnalyzed = CursorUtil.getColumnIndexOrThrow(_cursor, "isAnalyzed");
          final int _cursorIndexOfLastPlayedPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedPosition");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Episode> _result = new ArrayList<Episode>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Episode _item;
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
            _item = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
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
  public Flow<Episode> getEpisodeById(final String episodeId) {
    final String _sql = "SELECT * FROM episodes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, episodeId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"episodes"}, new Callable<Episode>() {
      @Override
      @Nullable
      public Episode call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPodcastId = CursorUtil.getColumnIndexOrThrow(_cursor, "podcastId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPublishDate = CursorUtil.getColumnIndexOrThrow(_cursor, "publishDate");
          final int _cursorIndexOfIsDownloaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isDownloaded");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsTranscribed = CursorUtil.getColumnIndexOrThrow(_cursor, "isTranscribed");
          final int _cursorIndexOfTranscriptionPath = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptionPath");
          final int _cursorIndexOfIsAnalyzed = CursorUtil.getColumnIndexOrThrow(_cursor, "isAnalyzed");
          final int _cursorIndexOfLastPlayedPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedPosition");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final Episode _result;
          if (_cursor.moveToFirst()) {
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
            _result = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
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
  public Flow<List<Episode>> getDownloadedEpisodes() {
    final String _sql = "SELECT * FROM episodes WHERE isDownloaded = 1 ORDER BY publishDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"episodes"}, new Callable<List<Episode>>() {
      @Override
      @NonNull
      public List<Episode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPodcastId = CursorUtil.getColumnIndexOrThrow(_cursor, "podcastId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPublishDate = CursorUtil.getColumnIndexOrThrow(_cursor, "publishDate");
          final int _cursorIndexOfIsDownloaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isDownloaded");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsTranscribed = CursorUtil.getColumnIndexOrThrow(_cursor, "isTranscribed");
          final int _cursorIndexOfTranscriptionPath = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptionPath");
          final int _cursorIndexOfIsAnalyzed = CursorUtil.getColumnIndexOrThrow(_cursor, "isAnalyzed");
          final int _cursorIndexOfLastPlayedPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedPosition");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Episode> _result = new ArrayList<Episode>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Episode _item;
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
            _item = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
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
  public Flow<List<Episode>> getTranscribedEpisodes() {
    final String _sql = "SELECT * FROM episodes WHERE isTranscribed = 1 ORDER BY publishDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"episodes"}, new Callable<List<Episode>>() {
      @Override
      @NonNull
      public List<Episode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPodcastId = CursorUtil.getColumnIndexOrThrow(_cursor, "podcastId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPublishDate = CursorUtil.getColumnIndexOrThrow(_cursor, "publishDate");
          final int _cursorIndexOfIsDownloaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isDownloaded");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsTranscribed = CursorUtil.getColumnIndexOrThrow(_cursor, "isTranscribed");
          final int _cursorIndexOfTranscriptionPath = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptionPath");
          final int _cursorIndexOfIsAnalyzed = CursorUtil.getColumnIndexOrThrow(_cursor, "isAnalyzed");
          final int _cursorIndexOfLastPlayedPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedPosition");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Episode> _result = new ArrayList<Episode>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Episode _item;
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
            _item = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
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
  public Flow<List<Episode>> getAnalyzedEpisodes() {
    final String _sql = "SELECT * FROM episodes WHERE isAnalyzed = 1 ORDER BY publishDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"episodes"}, new Callable<List<Episode>>() {
      @Override
      @NonNull
      public List<Episode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPodcastId = CursorUtil.getColumnIndexOrThrow(_cursor, "podcastId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPublishDate = CursorUtil.getColumnIndexOrThrow(_cursor, "publishDate");
          final int _cursorIndexOfIsDownloaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isDownloaded");
          final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
          final int _cursorIndexOfIsTranscribed = CursorUtil.getColumnIndexOrThrow(_cursor, "isTranscribed");
          final int _cursorIndexOfTranscriptionPath = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptionPath");
          final int _cursorIndexOfIsAnalyzed = CursorUtil.getColumnIndexOrThrow(_cursor, "isAnalyzed");
          final int _cursorIndexOfLastPlayedPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedPosition");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Episode> _result = new ArrayList<Episode>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Episode _item;
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
            _item = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
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
  public Flow<EpisodeWithAdSegments> getEpisodeWithAdSegments(final String episodeId) {
    final String _sql = "SELECT * FROM episodes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, episodeId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"ad_segments",
        "episodes"}, new Callable<EpisodeWithAdSegments>() {
      @Override
      @Nullable
      public EpisodeWithAdSegments call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfPodcastId = CursorUtil.getColumnIndexOrThrow(_cursor, "podcastId");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
            final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
            final int _cursorIndexOfPublishDate = CursorUtil.getColumnIndexOrThrow(_cursor, "publishDate");
            final int _cursorIndexOfIsDownloaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isDownloaded");
            final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
            final int _cursorIndexOfIsTranscribed = CursorUtil.getColumnIndexOrThrow(_cursor, "isTranscribed");
            final int _cursorIndexOfTranscriptionPath = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptionPath");
            final int _cursorIndexOfIsAnalyzed = CursorUtil.getColumnIndexOrThrow(_cursor, "isAnalyzed");
            final int _cursorIndexOfLastPlayedPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedPosition");
            final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
            final ArrayMap<String, ArrayList<AdSegment>> _collectionAdSegments = new ArrayMap<String, ArrayList<AdSegment>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionAdSegments.containsKey(_tmpKey)) {
                _collectionAdSegments.put(_tmpKey, new ArrayList<AdSegment>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipadSegmentsAscomPodcastr2DataModelAdSegment(_collectionAdSegments);
            final EpisodeWithAdSegments _result;
            if (_cursor.moveToFirst()) {
              final Episode _tmpEpisode;
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
              _tmpEpisode = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
              final ArrayList<AdSegment> _tmpAdSegmentsCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpAdSegmentsCollection = _collectionAdSegments.get(_tmpKey_1);
              _result = new EpisodeWithAdSegments(_tmpEpisode,_tmpAdSegmentsCollection);
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
  public Flow<List<EpisodeWithAdSegments>> getAnalyzedEpisodesWithAdSegments() {
    final String _sql = "SELECT * FROM episodes WHERE isAnalyzed = 1 ORDER BY publishDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"ad_segments",
        "episodes"}, new Callable<List<EpisodeWithAdSegments>>() {
      @Override
      @NonNull
      public List<EpisodeWithAdSegments> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfPodcastId = CursorUtil.getColumnIndexOrThrow(_cursor, "podcastId");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "audioUrl");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
            final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
            final int _cursorIndexOfPublishDate = CursorUtil.getColumnIndexOrThrow(_cursor, "publishDate");
            final int _cursorIndexOfIsDownloaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isDownloaded");
            final int _cursorIndexOfDownloadPath = CursorUtil.getColumnIndexOrThrow(_cursor, "downloadPath");
            final int _cursorIndexOfIsTranscribed = CursorUtil.getColumnIndexOrThrow(_cursor, "isTranscribed");
            final int _cursorIndexOfTranscriptionPath = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptionPath");
            final int _cursorIndexOfIsAnalyzed = CursorUtil.getColumnIndexOrThrow(_cursor, "isAnalyzed");
            final int _cursorIndexOfLastPlayedPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedPosition");
            final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
            final ArrayMap<String, ArrayList<AdSegment>> _collectionAdSegments = new ArrayMap<String, ArrayList<AdSegment>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionAdSegments.containsKey(_tmpKey)) {
                _collectionAdSegments.put(_tmpKey, new ArrayList<AdSegment>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipadSegmentsAscomPodcastr2DataModelAdSegment(_collectionAdSegments);
            final List<EpisodeWithAdSegments> _result = new ArrayList<EpisodeWithAdSegments>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final EpisodeWithAdSegments _item;
              final Episode _tmpEpisode;
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
              _tmpEpisode = new Episode(_tmpId,_tmpPodcastId,_tmpTitle,_tmpDescription,_tmpAudioUrl,_tmpImageUrl,_tmpDuration,_tmpPublishDate,_tmpIsDownloaded,_tmpDownloadPath,_tmpIsTranscribed,_tmpTranscriptionPath,_tmpIsAnalyzed,_tmpLastPlayedPosition,_tmpIsCompleted);
              final ArrayList<AdSegment> _tmpAdSegmentsCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpAdSegmentsCollection = _collectionAdSegments.get(_tmpKey_1);
              _item = new EpisodeWithAdSegments(_tmpEpisode,_tmpAdSegmentsCollection);
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

  private void __fetchRelationshipadSegmentsAscomPodcastr2DataModelAdSegment(
      @NonNull final ArrayMap<String, ArrayList<AdSegment>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipadSegmentsAscomPodcastr2DataModelAdSegment(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`episodeId`,`startTime`,`endTime`,`confidence`,`transcriptSnippet` FROM `ad_segments` WHERE `episodeId` IN (");
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
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "episodeId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfEpisodeId = 1;
      final int _cursorIndexOfStartTime = 2;
      final int _cursorIndexOfEndTime = 3;
      final int _cursorIndexOfConfidence = 4;
      final int _cursorIndexOfTranscriptSnippet = 5;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        final ArrayList<AdSegment> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final AdSegment _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final String _tmpEpisodeId;
          _tmpEpisodeId = _cursor.getString(_cursorIndexOfEpisodeId);
          final long _tmpStartTime;
          _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
          final long _tmpEndTime;
          _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
          final float _tmpConfidence;
          _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
          final String _tmpTranscriptSnippet;
          _tmpTranscriptSnippet = _cursor.getString(_cursorIndexOfTranscriptSnippet);
          _item_1 = new AdSegment(_tmpId,_tmpEpisodeId,_tmpStartTime,_tmpEndTime,_tmpConfidence,_tmpTranscriptSnippet);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
