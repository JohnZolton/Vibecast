package com.podcastr2.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.podcastr2.data.model.AdSegment;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AdSegmentDao_Impl implements AdSegmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AdSegment> __insertionAdapterOfAdSegment;

  private final EntityDeletionOrUpdateAdapter<AdSegment> __deletionAdapterOfAdSegment;

  private final EntityDeletionOrUpdateAdapter<AdSegment> __updateAdapterOfAdSegment;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByEpisodeId;

  public AdSegmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAdSegment = new EntityInsertionAdapter<AdSegment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `ad_segments` (`id`,`episodeId`,`startTime`,`endTime`,`confidence`,`transcriptSnippet`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AdSegment entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getEpisodeId());
        statement.bindLong(3, entity.getStartTime());
        statement.bindLong(4, entity.getEndTime());
        statement.bindDouble(5, entity.getConfidence());
        statement.bindString(6, entity.getTranscriptSnippet());
      }
    };
    this.__deletionAdapterOfAdSegment = new EntityDeletionOrUpdateAdapter<AdSegment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `ad_segments` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AdSegment entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAdSegment = new EntityDeletionOrUpdateAdapter<AdSegment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `ad_segments` SET `id` = ?,`episodeId` = ?,`startTime` = ?,`endTime` = ?,`confidence` = ?,`transcriptSnippet` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AdSegment entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getEpisodeId());
        statement.bindLong(3, entity.getStartTime());
        statement.bindLong(4, entity.getEndTime());
        statement.bindDouble(5, entity.getConfidence());
        statement.bindString(6, entity.getTranscriptSnippet());
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM ad_segments WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByEpisodeId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM ad_segments WHERE episodeId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final AdSegment adSegment, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAdSegment.insertAndReturnId(adSegment);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<AdSegment> adSegments,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfAdSegment.insertAndReturnIdsList(adSegments);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final AdSegment adSegment, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAdSegment.handle(adSegment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final AdSegment adSegment, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAdSegment.handle(adSegment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long adSegmentId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, adSegmentId);
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
  public Object deleteByEpisodeId(final String episodeId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByEpisodeId.acquire();
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
          __preparedStmtOfDeleteByEpisodeId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AdSegment>> getAdSegmentsByEpisodeId(final String episodeId) {
    final String _sql = "SELECT * FROM ad_segments WHERE episodeId = ? ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, episodeId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"ad_segments"}, new Callable<List<AdSegment>>() {
      @Override
      @NonNull
      public List<AdSegment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfTranscriptSnippet = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptSnippet");
          final List<AdSegment> _result = new ArrayList<AdSegment>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AdSegment _item;
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
            _item = new AdSegment(_tmpId,_tmpEpisodeId,_tmpStartTime,_tmpEndTime,_tmpConfidence,_tmpTranscriptSnippet);
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
  public Flow<AdSegment> getAdSegmentById(final long adSegmentId) {
    final String _sql = "SELECT * FROM ad_segments WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, adSegmentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"ad_segments"}, new Callable<AdSegment>() {
      @Override
      @Nullable
      public AdSegment call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfTranscriptSnippet = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptSnippet");
          final AdSegment _result;
          if (_cursor.moveToFirst()) {
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
            _result = new AdSegment(_tmpId,_tmpEpisodeId,_tmpStartTime,_tmpEndTime,_tmpConfidence,_tmpTranscriptSnippet);
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
  public Object getAdSegmentAtPosition(final String episodeId, final long position,
      final Continuation<? super AdSegment> $completion) {
    final String _sql = "SELECT * FROM ad_segments WHERE episodeId = ? AND ? BETWEEN startTime AND endTime LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, episodeId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, position);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AdSegment>() {
      @Override
      @Nullable
      public AdSegment call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfTranscriptSnippet = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptSnippet");
          final AdSegment _result;
          if (_cursor.moveToFirst()) {
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
            _result = new AdSegment(_tmpId,_tmpEpisodeId,_tmpStartTime,_tmpEndTime,_tmpConfidence,_tmpTranscriptSnippet);
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
  public Object getNextAdSegment(final String episodeId, final long currentPosition,
      final Continuation<? super AdSegment> $completion) {
    final String _sql = "SELECT * FROM ad_segments WHERE episodeId = ? AND startTime > ? ORDER BY startTime ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, episodeId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, currentPosition);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AdSegment>() {
      @Override
      @Nullable
      public AdSegment call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEpisodeId = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfTranscriptSnippet = CursorUtil.getColumnIndexOrThrow(_cursor, "transcriptSnippet");
          final AdSegment _result;
          if (_cursor.moveToFirst()) {
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
            _result = new AdSegment(_tmpId,_tmpEpisodeId,_tmpStartTime,_tmpEndTime,_tmpConfidence,_tmpTranscriptSnippet);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
