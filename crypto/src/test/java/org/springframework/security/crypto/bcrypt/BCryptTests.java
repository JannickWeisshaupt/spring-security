// Copyright (c) 2006 Damien Miller <djm@mindrot.org>
//
// Permission to use, copy, modify, and distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
package org.springframework.security.crypto.bcrypt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * JUnit unit tests for BCrypt routines
 *
 * @author Damien Miller
 */
public class BCryptTests {

	private static class TestObject<T> {

		private final T password;

		private final String salt;

		private final String expected;

		private TestObject(T password, String salt, String expected) {
			this.password = password;
			this.salt = salt;
			this.expected = expected;
		}

	}

	private static void print(String s) {
		// System.out.print(s);
	}

	private static void println(String s) {
		// System.out.println(s);
	}

	private static List<TestObject<String>> testObjectsString;

	private static List<TestObject<byte[]>> testObjectsByteArray;

	@BeforeAll
	public static void setupTestObjects() {
		testObjectsString = new ArrayList<>();
		testObjectsString.add(new TestObject<>("", "$2a$06$DCq7YPn5Rq63x1Lad4cll.",
				"$2a$06$DCq7YPn5Rq63x1Lad4cll.TV4S6ytwfsfvkgY8jIucDrjc8deX1s."));
		testObjectsString.add(new TestObject<>("", "$2a$08$HqWuK6/Ng6sg9gQzbLrgb.",
				"$2a$08$HqWuK6/Ng6sg9gQzbLrgb.Tl.ZHfXLhvt/SgVyWhQqgqcZ7ZuUtye"));
		testObjectsString.add(new TestObject<>("", "$2a$10$k1wbIrmNyFAPwPVPSVa/ze",
				"$2a$10$k1wbIrmNyFAPwPVPSVa/zecw2BCEnBwVS2GbrmgzxFUOqW9dk4TCW"));
		testObjectsString.add(new TestObject<>("", "$2a$12$k42ZFHFWqBp3vWli.nIn8u",
				"$2a$12$k42ZFHFWqBp3vWli.nIn8uYyIkbvYRvodzbfbK18SSsY.CsIQPlxO"));
		testObjectsString.add(new TestObject<>("", "$2b$06$8eVN9RiU8Yki430X.wBvN.",
				"$2b$06$8eVN9RiU8Yki430X.wBvN.LWaqh2962emLVSVXVZIXJvDYLsV0oFu"));
		testObjectsString.add(new TestObject<>("", "$2b$06$NlgfNgpIc6GlHciCkMEW8u",
				"$2b$06$NlgfNgpIc6GlHciCkMEW8uKOBsyvAp7QwlHpysOlKdtyEw50WQua2"));
		testObjectsString.add(new TestObject<>("", "$2y$06$mFDtkz6UN7B3GZ2qi2hhaO",
				"$2y$06$mFDtkz6UN7B3GZ2qi2hhaO3OFWzNEdcY84ELw6iHCPruuQfSAXBLK"));
		testObjectsString.add(new TestObject<>("", "$2y$06$88kSqVttBx.e9iXTPCLa5u",
				"$2y$06$88kSqVttBx.e9iXTPCLa5uFPrVFjfLH4D.KcO6pBiAmvUkvdg0EYy"));
		testObjectsString.add(new TestObject<>("a", "$2a$06$m0CrhHm10qJ3lXRY.5zDGO",
				"$2a$06$m0CrhHm10qJ3lXRY.5zDGO3rS2KdeeWLuGmsfGlMfOxih58VYVfxe"));
		testObjectsString.add(new TestObject<>("a", "$2a$08$cfcvVd2aQ8CMvoMpP2EBfe",
				"$2a$08$cfcvVd2aQ8CMvoMpP2EBfeodLEkkFJ9umNEfPD18.hUF62qqlC/V."));
		testObjectsString.add(new TestObject<>("a", "$2a$10$k87L/MF28Q673VKh8/cPi.",
				"$2a$10$k87L/MF28Q673VKh8/cPi.SUl7MU/rWuSiIDDFayrKk/1tBsSQu4u"));
		testObjectsString.add(new TestObject<>("a", "$2a$12$8NJH3LsPrANStV6XtBakCe",
				"$2a$12$8NJH3LsPrANStV6XtBakCez0cKHXVxmvxIlcz785vxAIZrihHZpeS"));
		testObjectsString.add(new TestObject<>("a", "$2b$06$ehKGYiS4wt2HAr7KQXS5z.",
				"$2b$06$ehKGYiS4wt2HAr7KQXS5z.OaRjB4jHO7rBHJKlGXbqEH3QVJfO7iO"));
		testObjectsString.add(new TestObject<>("a", "$2b$06$PWxFFHA3HiCD46TNOZh30e",
				"$2b$06$PWxFFHA3HiCD46TNOZh30eNto1hg5uM9tHBlI4q/b03SW/gGKUYk6"));
		testObjectsString.add(new TestObject<>("a", "$2y$06$LUdD6/aD0e/UbnxVAVbvGu",
				"$2y$06$LUdD6/aD0e/UbnxVAVbvGuUmIoJ3l/OK94ThhadpMWwKC34LrGEey"));
		testObjectsString.add(new TestObject<>("a", "$2y$06$eqgY.T2yloESMZxgp76deO",
				"$2y$06$eqgY.T2yloESMZxgp76deOROa7nzXDxbO0k.PJvuClTa.Vu1AuemG"));
		testObjectsString.add(new TestObject<>("abc", "$2a$06$If6bvum7DFjUnE9p2uDeDu",
				"$2a$06$If6bvum7DFjUnE9p2uDeDu0YHzrHM6tf.iqN8.yx.jNN1ILEf7h0i"));
		testObjectsString.add(new TestObject<>("abc", "$2a$08$Ro0CUfOqk6cXEKf3dyaM7O",
				"$2a$08$Ro0CUfOqk6cXEKf3dyaM7OhSCvnwM9s4wIX9JeLapehKK5YdLxKcm"));
		testObjectsString.add(new TestObject<>("abc", "$2a$10$WvvTPHKwdBJ3uk0Z37EMR.",
				"$2a$10$WvvTPHKwdBJ3uk0Z37EMR.hLA2W6N9AEBhEgrAOljy2Ae5MtaSIUi"));
		testObjectsString.add(new TestObject<>("abc", "$2a$12$EXRkfkdmXn2gzds2SSitu.",
				"$2a$12$EXRkfkdmXn2gzds2SSitu.MW9.gAVqa9eLS1//RYtYCmB1eLHg.9q"));
		testObjectsString.add(new TestObject<>("abc", "$2b$06$5FyQoicpbox1xSHFfhhdXu",
				"$2b$06$5FyQoicpbox1xSHFfhhdXuR2oxLpO1rYsQh5RTkI/9.RIjtoF0/ta"));
		testObjectsString.add(new TestObject<>("abc", "$2b$06$1kJyuho8MCVP3HHsjnRMkO",
				"$2b$06$1kJyuho8MCVP3HHsjnRMkO1nvCOaKTqLnjG2TX1lyMFbXH/aOkgc."));
		testObjectsString.add(new TestObject<>("abc", "$2y$06$ACfku9dT6.H8VjdKb8nhlu",
				"$2y$06$ACfku9dT6.H8VjdKb8nhluaoBmhJyK7GfoNScEfOfrJffUxoUeCjK"));
		testObjectsString.add(new TestObject<>("abc", "$2y$06$9JujYcoWPmifvFA3RUP90e",
				"$2y$06$9JujYcoWPmifvFA3RUP90e5rSEHAb5Ye6iv3.G9ikiHNv5cxjNEse"));
		testObjectsString.add(new TestObject<>("abcdefghijklmnopqrstuvwxyz", "$2a$06$.rCVZVOThsIa97pEDOxvGu",
				"$2a$06$.rCVZVOThsIa97pEDOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC"));
		testObjectsString.add(new TestObject<>("abcdefghijklmnopqrstuvwxyz", "$2a$08$aTsUwsyowQuzRrDqFflhge",
				"$2a$08$aTsUwsyowQuzRrDqFflhgekJ8d9/7Z3GV3UcgvzQW3J5zMyrTvlz."));
		testObjectsString.add(new TestObject<>("abcdefghijklmnopqrstuvwxyz", "$2a$10$fVH8e28OQRj9tqiDXs1e1u",
				"$2a$10$fVH8e28OQRj9tqiDXs1e1uxpsjN0c7II7YPKXua2NAKYvM6iQk7dq"));
		testObjectsString.add(new TestObject<>("abcdefghijklmnopqrstuvwxyz", "$2a$12$D4G5f18o7aMMfwasBL7Gpu",
				"$2a$12$D4G5f18o7aMMfwasBL7GpuQWuP3pkrZrOAnqP.bmezbMng.QwJ/pG"));
		testObjectsString.add(new TestObject<>("abcdefghijklmnopqrstuvwxyz", "$2b$06$O8E89AQPj1zJQA05YvIAU.",
				"$2b$06$O8E89AQPj1zJQA05YvIAU.hMpj25BXri1bupl/Q7CJMlpLwZDNBoO"));
		testObjectsString.add(new TestObject<>("abcdefghijklmnopqrstuvwxyz", "$2b$06$PDqIWr./o/P3EE/P.Q0A/u",
				"$2b$06$PDqIWr./o/P3EE/P.Q0A/uFg86WL/PXTbaW267TDALEwDylqk00Z."));
		testObjectsString.add(new TestObject<>("abcdefghijklmnopqrstuvwxyz", "$2y$06$34MG90ZLah8/ZNr3ltlHCu",
				"$2y$06$34MG90ZLah8/ZNr3ltlHCuz6bachF8/3S5jTuzF1h2qg2cUk11sFW"));
		testObjectsString.add(new TestObject<>("abcdefghijklmnopqrstuvwxyz", "$2y$06$AK.hSLfMyw706iEW24i68u",
				"$2y$06$AK.hSLfMyw706iEW24i68uKAc2yorPTrB0cimvjJHEBUrPkOq7VvG"));
		testObjectsString.add(new TestObject<>("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2a$06$fPIsBO8qRqkjj273rfaOI.",
				"$2a$06$fPIsBO8qRqkjj273rfaOI.HtSV9jLDpTbZn782DC6/t7qT67P6FfO"));
		testObjectsString.add(new TestObject<>("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2a$08$Eq2r4G/76Wv39MzSX262hu",
				"$2a$08$Eq2r4G/76Wv39MzSX262huzPz612MZiYHVUJe/OcOql2jo4.9UxTW"));
		testObjectsString.add(new TestObject<>("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2a$10$LgfYWkbzEvQ4JakH7rOvHe",
				"$2a$10$LgfYWkbzEvQ4JakH7rOvHe0y8pHKF9OaFgwUZ2q7W2FFZmZzJYlfS"));
		testObjectsString.add(new TestObject<>("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2a$12$WApznUOJfkEGSmYRfnkrPO",
				"$2a$12$WApznUOJfkEGSmYRfnkrPOr466oFDCaj4b6HY3EXGvfxm43seyhgC"));
		testObjectsString.add(new TestObject<>("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2b$06$FGWA8OlY6RtQhXBXuCJ8Wu",
				"$2b$06$FGWA8OlY6RtQhXBXuCJ8WusVipRI15cWOgJK8MYpBHEkktMfbHRIG"));
		testObjectsString.add(new TestObject<>("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2b$06$G6aYU7UhUEUDJBdTgq3CRe",
				"$2b$06$G6aYU7UhUEUDJBdTgq3CRekiopCN4O4sNitFXrf5NUscsVZj3a2r6"));
		testObjectsString.add(new TestObject<>("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2y$06$sYDFHqOcXTjBgOsqC0WCKe",
				"$2y$06$sYDFHqOcXTjBgOsqC0WCKeMd3T1UhHuWQSxncLGtXDLMrcE6vFDti"));
		testObjectsString.add(new TestObject<>("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2y$06$6Xm0gCw4g7ZNDCEp4yTise",
				"$2y$06$6Xm0gCw4g7ZNDCEp4yTisez0kSdpXEl66MvdxGidnmChIe8dFmMnq"));
		testObjectsByteArray = new ArrayList<>();
		testObjectsByteArray.add(new TestObject<>(new byte[] {}, "$2a$06$fPIsBO8qRqkjj273rfaOI.",
				"$2a$06$fPIsBO8qRqkjj273rfaOI.uiVGfgi6Z1Iz.vZr11mi/38o09TUVCy"));
		testObjectsByteArray.add(new TestObject<>(new byte[] {}, "$2a$08$Eq2r4G/76Wv39MzSX262hu",
				"$2a$08$Eq2r4G/76Wv39MzSX262hu2lrqIItOWKIkPsMMvm5LAFD.iVB7Nmm"));
		testObjectsByteArray.add(new TestObject<>(new byte[] {}, "$2a$10$LgfYWkbzEvQ4JakH7rOvHe",
				"$2a$10$LgfYWkbzEvQ4JakH7rOvHeU6pINYiHnazYxe4GikGWx9MaUr27Vpa"));
		testObjectsByteArray.add(new TestObject<>(new byte[] {}, "$2a$12$WApznUOJfkEGSmYRfnkrPO",
				"$2a$12$WApznUOJfkEGSmYRfnkrPONS3wcUvmKuh3LpjxSs6g78T77gZta3W"));
		testObjectsByteArray.add(new TestObject<>(new byte[] {}, "$2b$06$FGWA8OlY6RtQhXBXuCJ8Wu",
				"$2b$06$FGWA8OlY6RtQhXBXuCJ8Wu5oPJaT8BeCRmS273I6cpp5RwwjAWn7S"));
		testObjectsByteArray.add(new TestObject<>(new byte[] {}, "$2b$06$G6aYU7UhUEUDJBdTgq3CRe",
				"$2b$06$G6aYU7UhUEUDJBdTgq3CRebzUYAyG8MCS3WdBk0CcPb9bfj1.3cSG"));
		testObjectsByteArray.add(new TestObject<>(new byte[] {}, "$2y$06$sYDFHqOcXTjBgOsqC0WCKe",
				"$2y$06$sYDFHqOcXTjBgOsqC0WCKeOv88fqPKkuV1yGVh./TROmn1mL8gYh2"));
		testObjectsByteArray.add(new TestObject<>(new byte[] {}, "$2y$06$6Xm0gCw4g7ZNDCEp4yTise",
				"$2y$06$6Xm0gCw4g7ZNDCEp4yTisecBqTHmLJBHxTNZa8w2hupJKsIhPWOgG"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { -11 }, "$2a$06$fPIsBO8qRqkjj273rfaOI.",
				"$2a$06$fPIsBO8qRqkjj273rfaOI.AyMTPwvUEmZ2EdJM/p0S0eP3UQpBas."));
		testObjectsByteArray.add(new TestObject<>(new byte[] { -11 }, "$2a$08$Eq2r4G/76Wv39MzSX262hu",
				"$2a$08$Eq2r4G/76Wv39MzSX262huG.pmfTOWNaSXeVmr8y6qut1BpUiou6m"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { -11 }, "$2a$10$LgfYWkbzEvQ4JakH7rOvHe",
				"$2a$10$LgfYWkbzEvQ4JakH7rOvHeNm5INR.iq7bbwMewV0Tydrmqq3mZ5IK"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { -11 }, "$2a$12$WApznUOJfkEGSmYRfnkrPO",
				"$2a$12$WApznUOJfkEGSmYRfnkrPOi2qWwoWBJvfFzMrkqJwDedE3poicqwO"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { -11 }, "$2b$06$FGWA8OlY6RtQhXBXuCJ8Wu",
				"$2b$06$FGWA8OlY6RtQhXBXuCJ8Wuwip8vUd9WHq9onEGUjOS6CBHFkM./IG"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { -11 }, "$2b$06$G6aYU7UhUEUDJBdTgq3CRe",
				"$2b$06$G6aYU7UhUEUDJBdTgq3CRe6RQpRSN.PQ28XtDFT7zUVvpXNbg.K4i"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { -11 }, "$2y$06$sYDFHqOcXTjBgOsqC0WCKe",
				"$2y$06$sYDFHqOcXTjBgOsqC0WCKeduM9n5k0YfzTlgg69FIgGpw4ChTQNu2"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { -11 }, "$2y$06$6Xm0gCw4g7ZNDCEp4yTise",
				"$2y$06$6Xm0gCw4g7ZNDCEp4yTisetcxOr0uSWmFiVtNpDxjd5iaFWs/tyjG"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { 76, -56, -12, 9, -116 }, "$2a$06$fPIsBO8qRqkjj273rfaOI.",
				"$2a$06$fPIsBO8qRqkjj273rfaOI.5m8yX4eGfjqx/tyHtmte7/HbWtUS9u."));
		testObjectsByteArray.add(new TestObject<>(new byte[] { 76, -56, -12, 9, -116 }, "$2a$08$Eq2r4G/76Wv39MzSX262hu",
				"$2a$08$Eq2r4G/76Wv39MzSX262hu0Vc3YdKF53qtdTtZJKD7uQfsPeGfkP6"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { 76, -56, -12, 9, -116 }, "$2a$10$LgfYWkbzEvQ4JakH7rOvHe",
				"$2a$10$LgfYWkbzEvQ4JakH7rOvHeQBR1Mm2USNr//tnItwdVSZFNZfR/L9."));
		testObjectsByteArray.add(new TestObject<>(new byte[] { 76, -56, -12, 9, -116 }, "$2a$12$WApznUOJfkEGSmYRfnkrPO",
				"$2a$12$WApznUOJfkEGSmYRfnkrPO2WxEe4rN3gMECOFt21H8ozd661HB8Za"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { 76, -56, -12, 9, -116 }, "$2b$06$FGWA8OlY6RtQhXBXuCJ8Wu",
				"$2b$06$FGWA8OlY6RtQhXBXuCJ8Wu5SNpYypZvM0j3zTq7vSCtzqOllUArQW"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { 76, -56, -12, 9, -116 }, "$2b$06$G6aYU7UhUEUDJBdTgq3CRe",
				"$2b$06$G6aYU7UhUEUDJBdTgq3CRejcZ96XDmofwo2r3O/Lw0hoDHQy/Utxq"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { 76, -56, -12, 9, -116 }, "$2y$06$sYDFHqOcXTjBgOsqC0WCKe",
				"$2y$06$sYDFHqOcXTjBgOsqC0WCKej6.o3knVxc7obV8y47GTTFc9uUWC4OO"));
		testObjectsByteArray.add(new TestObject<>(new byte[] { 76, -56, -12, 9, -116 }, "$2y$06$6Xm0gCw4g7ZNDCEp4yTise",
				"$2y$06$6Xm0gCw4g7ZNDCEp4yTiseKCvXMhtv0IrQPu9d36a893DjJ880Vb6"));
	}

	/**
	 * Test method for 'BCrypt.hashpw(String, String)'
	 */
	@Test
	public void testHashpw() {
		print("BCrypt.hashpw(): ");
		for (TestObject<String> test : testObjectsString) {
			String hashed = BCrypt.hashpw(test.password, test.salt);
			assertThat(hashed).isEqualTo(test.expected);
			print(".");
		}
		println("");
	}

	/**
	 * Test method for 'BCrypt.hashpw(byte[], String)'
	 */
	@Test
	public void testHashpwByteArray() {
		for (TestObject<byte[]> test : testObjectsByteArray) {
			String hashed = BCrypt.hashpw(test.password, test.salt);
			assertThat(hashed).isEqualTo(test.expected);
		}
	}

	/**
	 * Test method for 'BCrypt.gensalt(int)'
	 */
	@Test
	public void testGensaltInt() {
		print("BCrypt.gensalt(log_rounds):");
		for (int i = 4; i <= 12; i++) {
			print(" " + Integer.toString(i) + ":");
			for (int j = 0; j < testObjectsString.size(); j += 4) {
				String plain = testObjectsString.get(j).password;
				String salt = BCrypt.gensalt(i);
				String hashed1 = BCrypt.hashpw(plain, salt);
				String hashed2 = BCrypt.hashpw(plain, hashed1);
				assertThat(hashed2).isEqualTo(hashed1);
				print(".");
			}
		}
		println("");
	}

	/**
	 * Test method for 'BCrypt.gensalt()'
	 */
	@Test
	public void testGensalt() {
		print("BCrypt.gensalt(): ");
		for (int i = 0; i < testObjectsString.size(); i += 4) {
			String plain = testObjectsString.get(i).password;
			String salt = BCrypt.gensalt();
			String hashed1 = BCrypt.hashpw(plain, salt);
			String hashed2 = BCrypt.hashpw(plain, hashed1);
			assertThat(hashed2).isEqualTo(hashed1);
			print(".");
		}
		println("");
	}

	/**
	 * Test method for 'BCrypt.checkpw(String, String)' expecting success
	 */
	@Test
	public void testCheckpw_success() {
		print("BCrypt.checkpw w/ good passwords: ");
		for (TestObject<String> test : testObjectsString) {
			assertThat(BCrypt.checkpw(test.password, test.expected)).isTrue();
			print(".");
		}
		println("");
	}

	/**
	 * Test method for 'BCrypt.checkpw(byte[], String)' expecting success
	 */
	@Test
	public void testCheckpwByteArray_success() {
		for (TestObject<byte[]> test : testObjectsByteArray) {
			assertThat(BCrypt.checkpw(test.password, test.expected)).isTrue();
		}
	}

	/**
	 * Test method for 'BCrypt.checkpw(String, String)' expecting failure
	 */
	@Test
	public void testCheckpw_failure() {
		print("BCrypt.checkpw w/ bad passwords: ");
		for (int i = 0; i < testObjectsString.size(); i++) {
			int broken_index = (i + 8) % testObjectsString.size();
			String plain = testObjectsString.get(i).password;
			String expected = testObjectsString.get(broken_index).expected;
			assertThat(BCrypt.checkpw(plain, expected)).isFalse();
			print(".");
		}
		println("");
	}

	/**
	 * Test method for 'BCrypt.checkpw(byte[], String)' expecting failure
	 */
	@Test
	public void testCheckpwByteArray_failure() {
		for (int i = 0; i < testObjectsByteArray.size(); i++) {
			int broken_index = (i + 8) % testObjectsByteArray.size();
			byte[] plain = testObjectsByteArray.get(i).password;
			String expected = testObjectsByteArray.get(broken_index).expected;
			assertThat(BCrypt.checkpw(plain, expected)).isFalse();
		}
	}

	/**
	 * Test for correct hashing of non-US-ASCII passwords
	 */
	@Test
	public void testInternationalChars() {
		print("BCrypt.hashpw w/ international chars: ");
		String pw1 = "ππππππππ";
		String pw2 = "????????";
		String h1 = BCrypt.hashpw(pw1, BCrypt.gensalt());
		assertThat(BCrypt.checkpw(pw2, h1)).isFalse();
		print(".");
		String h2 = BCrypt.hashpw(pw2, BCrypt.gensalt());
		assertThat(BCrypt.checkpw(pw1, h2)).isFalse();
		print(".");
		println("");
	}

	@Test
	public void roundsForDoesNotOverflow() {
		assertThat(BCrypt.roundsForLogRounds(10)).isEqualTo(1024);
		assertThat(BCrypt.roundsForLogRounds(31)).isEqualTo(0x80000000L);
	}

	@Test
	public void emptyByteArrayCannotBeEncoded() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> BCrypt.encode_base64(new byte[0], 0, new StringBuilder()));
	}

	@Test
	public void moreBytesThanInTheArrayCannotBeEncoded() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> BCrypt.encode_base64(new byte[1], 2, new StringBuilder()));
	}

	@Test
	public void decodingMustRequestMoreThanZeroBytes() {
		assertThatIllegalArgumentException().isThrownBy(() -> BCrypt.decode_base64("", 0));
	}

	private static String encode_base64(byte d[], int len) throws IllegalArgumentException {
		StringBuilder rs = new StringBuilder();
		BCrypt.encode_base64(d, len, rs);
		return rs.toString();
	}

	@Test
	public void testBase64EncodeSimpleByteArrays() {
		assertThat(encode_base64(new byte[] { 0 }, 1)).isEqualTo("..");
		assertThat(encode_base64(new byte[] { 0, 0 }, 2)).isEqualTo("...");
		assertThat(encode_base64(new byte[] { 0, 0, 0 }, 3)).isEqualTo("....");
	}

	@Test
	public void decodingCharsOutsideAsciiGivesNoResults() {
		byte[] ba = BCrypt.decode_base64("ππππππππ", 1);
		assertThat(ba).isEmpty();
	}

	@Test
	public void decodingStopsWithFirstInvalidCharacter() {
		assertThat(BCrypt.decode_base64("....", 1)).hasSize(1);
		assertThat(BCrypt.decode_base64(" ....", 1)).isEmpty();
	}

	@Test
	public void decodingOnlyProvidesAvailableBytes() {
		assertThat(BCrypt.decode_base64("", 1)).isEmpty();
		assertThat(BCrypt.decode_base64("......", 3)).hasSize(3);
		assertThat(BCrypt.decode_base64("......", 4)).hasSize(4);
		assertThat(BCrypt.decode_base64("......", 5)).hasSize(4);
	}

	/**
	 * Encode and decode each byte value in each position.
	 */
	@Test
	public void testBase64EncodeDecode() {
		byte[] ba = new byte[3];
		for (int b = 0; b <= 0xFF; b++) {
			for (int i = 0; i < ba.length; i++) {
				Arrays.fill(ba, (byte) 0);
				ba[i] = (byte) b;
				String s = encode_base64(ba, 3);
				assertThat(s).hasSize(4);
				byte[] decoded = BCrypt.decode_base64(s, 3);
				assertThat(decoded).isEqualTo(ba);
			}
		}
	}

	@Test
	public void genSaltFailsWithTooFewRounds() {
		assertThatIllegalArgumentException().isThrownBy(() -> BCrypt.gensalt(3));
	}

	@Test
	public void genSaltFailsWithTooManyRounds() {
		assertThatIllegalArgumentException().isThrownBy(() -> BCrypt.gensalt(32));
	}

	@Test
	public void genSaltGeneratesCorrectSaltPrefix() {
		assertThat(BCrypt.gensalt(4)).startsWith("$2a$04$");
		assertThat(BCrypt.gensalt(31)).startsWith("$2a$31$");
	}

	@Test
	public void hashpwFailsWhenSaltIsNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> BCrypt.hashpw("password", null));
	}

	@Test
	public void hashpwFailsWhenSaltSpecifiesTooFewRounds() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> BCrypt.hashpw("password", "$2a$03$......................"));
	}

	@Test
	public void hashpwFailsWhenSaltSpecifiesTooManyRounds() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> BCrypt.hashpw("password", "$2a$32$......................"));
	}

	@Test
	public void saltLengthIsChecked() {
		assertThatIllegalArgumentException().isThrownBy(() -> BCrypt.hashpw("", ""));
	}

	@Test
	public void hashpwWorksWithOldRevision() {
		assertThat(BCrypt.hashpw("password", "$2$05$......................"))
				.isEqualTo("$2$05$......................bvpG2UfzdyW/S0ny/4YyEZrmczoJfVm");
	}

	@Test
	public void hashpwFailsWhenSaltIsTooShort() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> BCrypt.hashpw("password", "$2a$10$123456789012345678901"));
	}

	@Test
	public void equalsOnStringsIsCorrect() {
		assertThat(BCrypt.equalsNoEarlyReturn("", "")).isTrue();
		assertThat(BCrypt.equalsNoEarlyReturn("test", "test")).isTrue();
		assertThat(BCrypt.equalsNoEarlyReturn("test", "")).isFalse();
		assertThat(BCrypt.equalsNoEarlyReturn("", "test")).isFalse();
		assertThat(BCrypt.equalsNoEarlyReturn("test", "pass")).isFalse();
	}

	@Test
	public void checkpwWhenZeroRoundsThenMatches() {
		String password = "$2a$00$9N8N35BVs5TLqGL3pspAte5OWWA2a2aZIs.EGp7At7txYakFERMue";
		assertThat(BCrypt.checkpw("password", password)).isTrue();
		assertThat(BCrypt.checkpw("wrong", password)).isFalse();
	}

}
