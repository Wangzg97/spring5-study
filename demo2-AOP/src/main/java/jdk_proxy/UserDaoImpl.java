package jdk_proxy;

/**
 * @author: wangzg
 * @date 2021/5/12 22:16
 * modified by: wangzg
 */
public class UserDaoImpl implements UserDao{
    @Override
    public int test(int x) {
        return x;
    }

    @Override
    public String test2(String v) {
        return v;
    }
}
