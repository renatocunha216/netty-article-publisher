// automatically generated by the FlatBuffers compiler, do not modify

package br.com.rbcti.publisher.common.messages.fbs;

import com.google.flatbuffers.BaseVector;
import com.google.flatbuffers.BooleanVector;
import com.google.flatbuffers.ByteVector;
import com.google.flatbuffers.Constants;
import com.google.flatbuffers.DoubleVector;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.FloatVector;
import com.google.flatbuffers.IntVector;
import com.google.flatbuffers.LongVector;
import com.google.flatbuffers.ShortVector;
import com.google.flatbuffers.StringVector;
import com.google.flatbuffers.Struct;
import com.google.flatbuffers.Table;
import com.google.flatbuffers.UnionVector;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class LoginRequestFbs extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_23_5_26(); }
  public static LoginRequestFbs getRootAsLoginRequestFbs(ByteBuffer _bb) { return getRootAsLoginRequestFbs(_bb, new LoginRequestFbs()); }
  public static LoginRequestFbs getRootAsLoginRequestFbs(ByteBuffer _bb, LoginRequestFbs obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public LoginRequestFbs __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public br.com.rbcti.publisher.common.messages.fbs.HeaderFbs header() { return header(new br.com.rbcti.publisher.common.messages.fbs.HeaderFbs()); }
  public br.com.rbcti.publisher.common.messages.fbs.HeaderFbs header(br.com.rbcti.publisher.common.messages.fbs.HeaderFbs obj) { int o = __offset(4); return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null; }
  public String userName() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer userNameAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer userNameInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String password() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer passwordAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer passwordInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }

  public static int createLoginRequestFbs(FlatBufferBuilder builder,
      int headerOffset,
      int userNameOffset,
      int passwordOffset) {
    builder.startTable(3);
    LoginRequestFbs.addPassword(builder, passwordOffset);
    LoginRequestFbs.addUserName(builder, userNameOffset);
    LoginRequestFbs.addHeader(builder, headerOffset);
    return LoginRequestFbs.endLoginRequestFbs(builder);
  }

  public static void startLoginRequestFbs(FlatBufferBuilder builder) { builder.startTable(3); }
  public static void addHeader(FlatBufferBuilder builder, int headerOffset) { builder.addOffset(0, headerOffset, 0); }
  public static void addUserName(FlatBufferBuilder builder, int userNameOffset) { builder.addOffset(1, userNameOffset, 0); }
  public static void addPassword(FlatBufferBuilder builder, int passwordOffset) { builder.addOffset(2, passwordOffset, 0); }
  public static int endLoginRequestFbs(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public LoginRequestFbs get(int j) { return get(new LoginRequestFbs(), j); }
    public LoginRequestFbs get(LoginRequestFbs obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

