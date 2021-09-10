package cmd.setCmd;

import args.HkArgs;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Element;
import testUtil.GlobalConst;
import xml.database.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Matchers.anyObject;

@ExtendWith(MockitoExtension.class)
class SetCmdTest {
    static Database _db = new DatabaseBuilder(
            GlobalConst.dbName,
            GlobalConst.masterKey,
            GlobalConst.huskeyDir
    ).build();

    Database useDatabase() {
        return new DatabaseBuilder(
                GlobalConst.dbName,
                GlobalConst.masterKey,
                GlobalConst.huskeyDir
        ).build();
    }

    @AfterAll
    static void cleanUp() {
        _db.write(); // テスト中に変更されたデータベースを元に戻す
    }

    /*
     * テスト時に生成されるデータの種類
     *
     * - huskey1: パスワードを自動生成してデータを作成・更新
     * - huskey2: データベースを指定し、パスワードを自動生成してデータを作成・更新
     * - huskey3: パスワードを手動で指定してデータを作成・更新
     * - huskey4: パスワード生成の設定を指定し、その設定でパスワードを自動生成してデータを作成・更新
     *
     * パスワードを自動生成した場合、password要素のinput属性はfalseとなり、手動で指定した場合はtrueとなる。
     * これは更新時にも適用される。
     */

    @Nested
    class 新規作成 {
        @Nested
        class パスワードを自動生成 {
            String target = "huskey1";
            HkArgs hkArgs = new HkArgs(
                    "set",
                    new String[]{"huskey1"},
                    new String[]{""}
            );

            @Spy
            SetCmd setCmd = new SetCmd(hkArgs, GlobalConst.huskeyDir);

            @Test
            void エラーなく実行されるか() {
                doReturn(GlobalConst.masterKey).when(setCmd).readMasterKey();
                setCmd.run();
            }

            @Test
            void 新しいデータが追加されているか() {
                Database db = useDatabase();
                assertTrue(db.exists("//data[@title='" + target + "']"));
            }
        }

        @Nested
        class データベースを指定しパスワードを自動生成 {
            String target = "huskey2";
            HkArgs hkArgs = new HkArgs(
                    "set",
                    new String[]{target},
                    new String[]{"--db=SampleDB"}
            );

            @Spy
            SetCmd setCmd = new SetCmd(hkArgs, GlobalConst.huskeyDir);

            @Test
            void エラーなく実行されるか() {
                doReturn(GlobalConst.masterKey).when(setCmd).readMasterKey();
                setCmd.run();
            }

            @Test
            void 新しいデータが追加されているか() {
                Database db = useDatabase();
                assertTrue(db.exists("//data[@title='" + target + "']"));
            }
        }

        @Nested
        class パスワードを手動で更新 {
            String target = "huskey3";
            HkArgs hkArgs = new HkArgs(
                    "set",
                    new String[]{target},
                    new String[]{"-i"}
            );

            @Spy
            SetCmd setCmd = new SetCmd(hkArgs, GlobalConst.huskeyDir);

            @Test
            void エラーなく実行されるか() {
                doReturn(GlobalConst.masterKey).when(setCmd).readMasterKey();
                setCmd.run();
            }

            @Test
            void 新しいデータが追加されているか() {
                Database db = useDatabase();
                assertTrue(db.exists("//data[@title='" + target + "']"));
            }
        }

        @Nested
        class パスワード生成の設定を選択して生成 {
            String target = "huskey4";
            HkArgs hkArgs = new HkArgs(
                    "set",
                    new String[]{target},
                    new String[]{"-s"}
            );
            Password buildPasswd() {
                CharSet charSet = new CharSet(
                        "true",
                        "true",
                        "false",
                        "false",
                        "false",
                        "false"
                );
                return new Password("20", charSet, "");
            }

            @Spy
            SetCmd setCmd = new SetCmd(hkArgs, GlobalConst.huskeyDir);

            @Test
            void エラーなく実行されるか() {
                doReturn(GlobalConst.masterKey).when(setCmd).readMasterKey();
                doReturn(buildPasswd()).when(setCmd).buildPasswd(anyObject(), anyObject());
                setCmd.run();
            }

            Database db;

            @Test
            void 新しいデータが追加されているか() {
                db = useDatabase();
                assertTrue(db.exists("//data[@title='" + target + "']"));
            }

            @Test
            void 指定した設定に変更されているか() {
                db = useDatabase();
                Element password = (Element) db.searchNode("//data[@title='" + target + "']/password");
                // TODO: 設定を比較する
            }
        }
    }

    @Nested
    class 更新 {
        @Nested
        class パスワードを自動生成で更新 {
            String target = "Discord";
            HkArgs hkArgs = new HkArgs(
                    "set",
                    new String[]{target},
                    new String[]{""}
            );
            Database beforeDB = useDatabase();

            @Spy
            SetCmd setCmd = new SetCmd(hkArgs, GlobalConst.huskeyDir);

            @Test
            void エラーなく実行されるか() {
                doReturn(GlobalConst.masterKey).when(setCmd).readMasterKey();
                setCmd.run();
            }

            @Test
            void 更新前後でパスワードが変わっているか() {
                Database afterDB = useDatabase();

                String expr = "//data[@title='" + target +"']/password/value";
                String beforeValue = beforeDB.searchNode(expr).getTextContent();
                String afterValue = afterDB.searchNode(expr).getTextContent();

                assertNotEquals(beforeValue, afterValue);
            }
        }

        @Nested
        class input属性がfalseのパスワードを手動更新 {
            String target = "Twitter";
            HkArgs hkArgs = new HkArgs(
                    "set",
                    new String[]{target},
                    new String[]{"-i"}
            );

            @Spy
            SetCmd setCmd = new SetCmd(hkArgs, GlobalConst.huskeyDir);

            @Test
            void エラーなく実行されるか() {
                doReturn(GlobalConst.masterKey).when(setCmd).readMasterKey();
                setCmd.run();
            }

            @Test
            void input属性がtrueに変わっているか() {
                Database db = useDatabase();
                Element data = (Element) db.searchNode("//data[@title='" + target + "']/password");
                assertEquals("true", data.getAttribute("input"));
            }
        }

        @Nested
        class input属性がtrueのパスワードを自動生成で更新 {
            String target = "Google";
            HkArgs hkArgs = new HkArgs(
                    "set",
                    new String[]{target},
                    new String[]{""}
            );

            @Spy
            SetCmd setCmd = new SetCmd(hkArgs, GlobalConst.huskeyDir);

            @Test
            void エラーなく実行されるか() {
                doReturn(GlobalConst.masterKey).when(setCmd).readMasterKey();
                setCmd.run();
            }

            @Test
            void input属性がfalseに変わっているか() {
                Database db = useDatabase();
                Element data = (Element) db.searchNode("//data[@title='" + target + "']/password");
                assertEquals("false", data.getAttribute("input"));
            }
        }
    }
}